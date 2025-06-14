plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.apptechbd.nibay"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.apptechbd.nibay"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11

        // ✅ Enable desugaring
        isCoreLibraryDesugaringEnabled = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.play.services.auth.api.phone)
    implementation(libs.play.services.basement)

    // leakCanary is a memory leak detection library for Android.
    // debugImplementation because LeakCanary should only run in debug builds.
    debugImplementation(libs.leakcanary.android)

    // lottie animation library
    implementation(libs.lottie)

    // flexbox layout
    implementation(libs.flexbox)

    // PageIndicator pagination
    implementation(libs.dotsindicator)

    //circle image view
    implementation(libs.circleimageview)

    //Glide
    implementation(libs.glide)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    //shimmer layout
    implementation(libs.shimmer)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // ✅ Use version catalog for desugar_jdk_libs
    coreLibraryDesugaring(libs.desugar.jdk.libs)

    //image cropping library
    implementation(libs.ucrop)
}