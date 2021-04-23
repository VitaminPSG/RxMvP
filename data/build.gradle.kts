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
}

dependencies {
    implementation(project(":domain"))

    implementation(Dependencies.kotlin)
    implementation(Dependencies.kotlinReflect)

    implementation(Dependencies.coreKtx)

    implementation(Dependencies.rxJava3)
    implementation(Dependencies.rxJava3Android)
    implementation(Dependencies.rxJava3Kotlin)


    implementation(Dependencies.okHttp)
    implementation(Dependencies.okHttpLogging)
    implementation(Dependencies.retrofit)
    implementation(Dependencies.retrofitRxJavaAdapter)

    implementation(Dependencies.moshi)
    kapt(Dependencies.moshiCodegen)

    implementation(Dependencies.roomRuntime)
    implementation(Dependencies.roomRxJava)
    kapt(Dependencies.roomCompiler)

    testImplementation(TestDependencies.junit)
    testImplementation(Dependencies.kotlinReflect)
    testImplementation(TestDependencies.koinTest)
    testImplementation(TestDependencies.mockk)
}