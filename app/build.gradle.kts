plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.myeshop"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.myeshop"
        minSdk = 24
        targetSdk = 35
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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.4.1") // Για την Toolbar
    implementation("androidx.recyclerview:recyclerview:1.2.1") // Για το RecyclerView
    implementation("com.google.android.material:material:1.5.0") // Για το Material Design
    testImplementation("junit:junit:4.13.2") // Εξάρτηση για τα τεστ
    androidTestImplementation("androidx.test.ext:junit:1.1.3") // Εξάρτηση για τα Android tests
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0") // Εξάρτηση για Espresso
}
