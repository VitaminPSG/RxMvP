plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("de.mannodermaus.android-junit5")
    id("kotlin-android")
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

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":domain"))

    implementation(Dependencies.kotlin)
    implementation(Dependencies.kotlinReflect)
    implementation(Dependencies.coreKtx)

    implementation(Dependencies.timber)

    implementation(Dependencies.koinAndroidScopes)

    implementation(Dependencies.appCompat)
    implementation(Dependencies.material)
    implementation(Dependencies.constraintLayout)
    implementation(Dependencies.recyclerView)

    implementation(Dependencies.viewAutoBinding)

    implementation(Dependencies.rxBinding)
    implementation(Dependencies.rxBindingMaterial)
    implementation(Dependencies.rxBindingSwipeRefreshLayout)
    implementation(Dependencies.rxBindingRecyclerView)

    implementation(Dependencies.navFragmentKtx)
    implementation(Dependencies.navUiKtx)

    implementation(Dependencies.browser)

    implementation(Dependencies.rxJava3)
    implementation(Dependencies.rxJava3Android)
    implementation(Dependencies.rxJava3Kotlin)

    testImplementation(TestDependencies.junit)
    testImplementation(Dependencies.kotlinReflect)
    testImplementation(TestDependencies.koinTest)
    testImplementation(TestDependencies.mockk)
}