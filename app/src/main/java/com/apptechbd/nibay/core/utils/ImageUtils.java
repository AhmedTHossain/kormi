package com.apptechbd.nibay.core.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ImageUtils {
    public static File getImageFileFromUri(Context context, Uri imageUri) {
        File imageFile = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            // Create a temporary file to store the image
            String tempFileName = "temp_image." + getFileExtension(context, imageUri);
            File outputDir;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                outputDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Temp");
            } else {
                outputDir = new File(Environment.getExternalStorageDirectory(), "Temp");
            }
            outputDir.mkdirs();
            imageFile = new File(outputDir, tempFileName);

            // Open input and output streams
            ContentResolver contentResolver = context.getContentResolver();
            inputStream = contentResolver.openInputStream(imageUri);
            outputStream = new FileOutputStream(imageFile);

            // Copy the image data from input stream to output stream
            byte[] buffer = new byte[4 * 1024]; // 4 KB buffer
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            return imageFile;
        } catch (IOException e) {
            // Handle any errors that occurred during the process
            e.printStackTrace();
            if (imageFile != null) {
                imageFile.delete(); // Delete the temporary file if an error occurred
            }
            return null;
        } finally {
            // Close the streams
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String getFileExtension(Context context, Uri uri) {
        // Get the file extension from the image URI
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        String extension = mime.getExtensionFromMimeType(context.getContentResolver().getType(uri));
        if (extension == null) {
            extension = "jpg"; // Default to JPG format if extension is null
        }
        return extension;
    }

    public File rotateImage(Uri uri, Context context) {
        File imageFile = getImageFileFromUri(context, uri);
        try {
            ExifInterface exif = new ExifInterface(imageFile);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

            Matrix matrix = new Matrix();
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    matrix.postRotate(90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    matrix.postRotate(180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    matrix.postRotate(270);
                    break;
                default:
                    // No rotation needed
                    break;
            }
            Bitmap originalBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            Bitmap rotatedBitmap = Bitmap.createBitmap(originalBitmap, 0, 0, originalBitmap.getWidth(), originalBitmap.getHeight(), matrix, true);

            File rotatedFile = new File(context.getCacheDir(), "rotated_image.jpg");
            try (FileOutputStream out = new FileOutputStream(rotatedFile)) {
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageFile = rotatedFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageFile;
    }
}
