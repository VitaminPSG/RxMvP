plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("de.mannodermaus.android-junit5")
}

android {
    compileSdkVersion(AndroidVersions.compileSdkVersion)

    defaultConfig {
        minSdkVersion(AndroidVersions.minSdkVersion)
        targetSdkVersion(AndroidVersions.targetSdkVersion)
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(Dependencies.kotlin)
    implementation(Dependencies.kotlinReflect)

    implementation(Dependencies.coreKtx)
    implementation(Dependencies.rxJava3)
    implementation(Dependencies.rxJava3Android)
    implementation(Dependencies.rxJava3Kotlin)

    testImplementation(TestDependencies.junit)
    testImplementation(Dependencies.kotlinReflect)
    testImplementation(TestDependencies.koinTest)
    testImplementation(TestDependencies.mockk)
}