plugins {
    id("com.android.application")
}

android {
    // 👇 ВОТ ЭТА СТРОКА РЕШАЕТ ПРОБЛЕМУ
    namespace = "com.example.abiturientpro"
    // Она должна соответствовать пакету, указанному в AndroidManifest.xml

    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.abiturientpro"
        minSdk = 21
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment:2.7.1")
    implementation("androidx.navigation:navigation-ui:2.7.1")
    implementation("androidx.preference:preference:1.2.1")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}