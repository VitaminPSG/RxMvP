plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("de.mannodermaus.android-junit5")
    id("com.google.firebase.crashlytics")
    id("androidx.navigation.safeargs.kotlin")
    Plugins.configureGoogleServices {
        id("com.google.gms.google-services")
    }
}

android {
    compileSdkVersion(AndroidVersions.compileSdkVersion)

    defaultConfig {
        applicationId = "com.android.rxmvp"
        minSdkVersion(AndroidVersions.minSdkVersion)
        targetSdkVersion(AndroidVersions.targetSdkVersion)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArgument(
            "runnerBuilder",
            "de.mannodermaus.junit5.AndroidJUnit5Builder"
        )
        versionName = AppVersion.versionName
        versionCode = AppVersion.versionCode
    }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
            isTestCoverageEnabled = true
            buildConfigField("String", "BASE_URL", "\"https://api.seatgeek.com\"")
            buildConfigField("String", "CLIENT_ID", "\"MjE3MTU3MjV8MTYxODQxMDMzNS44NzY4MDQ4\"")
//            signingConfig = signingConfigs.findByName("debugConf")
        }
        getByName("release") {
            isMinifyEnabled = false
            isShrinkResources = false
            isUseProguard = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
//            signingConfig = signingConfigs.findByName("releaseConf")
        }
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    packagingOptions {
        exclude("META-INF/proguard/androidx-annotations.pro")
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/LICENSE")
        exclude("META-INF/LICENSE.md")
        exclude("META-INF/LICENSE-notice.md")
        exclude("META-INF/LICENSE.txt")
        exclude("META-INF/license.txt")
        exclude("META-INF/NOTICE")
        exclude("META-INF/NOTICE.txt")
        exclude("META-INF/notice.txt")
        exclude("META-INF/ASL2.0")
    }
}

dependencies {
    implementation(project(":presentation"))
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(Dependencies.kotlin)
    implementation(Dependencies.kotlinReflect)

    implementation(Dependencies.coreKtx)

    implementation(Dependencies.timber)

    implementation(Dependencies.koinAndroidScopes)

    implementation(Dependencies.okHttp)
    implementation(Dependencies.okHttpLogging)
    implementation(Dependencies.retrofit)
    implementation(Dependencies.retrofitRxJavaAdapter)
    implementation(Dependencies.retrofitMoshiConverter)

    implementation(Dependencies.moshi)
    kapt(Dependencies.moshiCodegen)

    testImplementation(TestDependencies.junit)
    testImplementation(Dependencies.kotlinReflect)
    testImplementation(TestDependencies.koinTest)
    testImplementation(TestDependencies.mockk)

    androidTestImplementation(TestDependencies.junit)
    androidTestImplementation(Dependencies.kotlinReflect)
    androidTestImplementation(TestDependencies.koinTest)
    androidTestImplementation(TestDependencies.mockitoAndroid)

    androidTestImplementation(TestDependencies.androidxTestRunner)
    androidTestImplementation(TestDependencies.androidxTestRules)
    androidTestImplementation(TestDependencies.androidxTestCoreKtx)

    androidTestImplementation(TestDependencies.mannodermausCore)
    androidTestRuntimeOnly(TestDependencies.mannodermausTestRunner)

    androidTestImplementation(TestDependencies.jupiterApi)
    androidTestImplementation(TestDependencies.jupiterEngine)
    androidTestImplementation(TestDependencies.jupiterParams)

    androidTestImplementation(TestDependencies.espressoCore)
    androidTestImplementation(TestDependencies.espressoIntents)

    androidTestImplementation(TestDependencies.kakao)
}
