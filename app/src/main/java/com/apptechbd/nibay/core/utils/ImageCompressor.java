package com.apptechbd.nibay.core.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageCompressor {

    public static File compressImage(Context context, File originalFile, String compressedFileName) throws IOException {
        // Decode the original file to a bitmap
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(originalFile.getAbsolutePath(), options);

        // Create a new file to save compressed image
        File compressedFile = new File(context.getCacheDir(), compressedFileName);

        FileOutputStream out = new FileOutputStream(compressedFile);

        // Compress the image to JPEG at 70% quality (adjust as needed)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, out);

        out.flush();
        out.close();

        return compressedFile;
    }
}
