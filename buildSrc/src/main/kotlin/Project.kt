@file:Suppress("MaximumLineLength", "MaxLineLength")

private object SharedVersions {

    const val koin = "2.1.5"
    const val kotlin = "1.4.32"
}

object AppVersion {

    const val versionName = "1.0.0"
    const val versionCode = 26
}

object AndroidVersions {

    const val minSdkVersion = 23
    const val compileSdkVersion = 29
    const val targetSdkVersion = 29
}

object Plugins {
    private object Versions {

        const val androidGradle = "4.0.0"
        const val crashlytics = "2.1.0"
        const val googleServices = "4.3.4"

        const val junit5 = "1.6.0.0"
    }

    private object Config {

        const val googleServices = false
    }

    fun configureGoogleServices(configuration: () -> Unit) {
        if (Config.googleServices) {
            configuration()
        }
    }

    const val androidGradle = "com.android.tools.build:gradle:${Versions.androidGradle}"
    const val navigationSafeArgs =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Dependencies.Versions.navKtx}"
    const val crashlytics = "com.google.firebase:firebase-crashlytics-gradle:${Versions.crashlytics}"
    const val kotlinGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${SharedVersions.kotlin}"
    const val googleServices = "com.google.gms:google-services:${Versions.googleServices}"
    const val junit5 = "de.mannodermaus.gradle.plugins:android-junit5:${Versions.junit5}"
}

object Dependencies {
    object Versions {

        const val androidAppCompat = "1.2.0"
        const val coreKtx = "1.3.2"
        const val material = "1.3.0"
        const val constraintLayout = "2.0.4"
        const val crashlytics = "17.2.1"
        const val firebaseStorage = "19.2.0"
        const val firebaseDatabase = "19.3.0"
        const val timber = "4.7.1"
        const val leakCanary = "2.6"
        const val lifecycleExtensions = "2.2.0"
        const val lifecycleRuntime = "2.3.1"
        const val lifecycleViewModel = "2.3.1"
        const val moshi = "1.11.0"
        const val navKtx = "2.3.5"

        const val retrofit = "2.9.0"
        const val okHttp = "4.9.1"
        const val recyclerView = "1.2.0"
        const val glide = "4.11.0"

        const val rxJava3 = "3.0.12"
        const val rxJava3Android = "3.0.0"

        const val rxBinding = "4.0.0"
        const val viewAutoBinding = "1.3.1"

        const val room = "2.3.0-rc01"

        const val browser = "1.3.0"
    }

    // Kotlin
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${SharedVersions.kotlin}"
    const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:${SharedVersions.kotlin}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"

    // Network
    const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    const val okHttpLogging = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitMoshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    const val retrofitRxJavaAdapter = "com.squareup.retrofit2:adapter-rxjava3:${Versions.retrofit}"

    // RxJava
    const val rxJava3 = "io.reactivex.rxjava3:rxjava:${Versions.rxJava3}"
    const val rxJava3Android = "io.reactivex.rxjava3:rxandroid:${Versions.rxJava3Android}"
    const val rxJava3Kotlin = "io.reactivex.rxjava3:rxkotlin:${Versions.rxJava3Android}"

    // ViewAutoBinding
    const val viewAutoBinding =
        "com.kirich1409.viewbindingpropertydelegate:vbpd-noreflection:${Versions.viewAutoBinding}"

    // RxViewBinding
    const val rxBinding = "com.jakewharton.rxbinding4:rxbinding:${Versions.rxBinding}"
    const val rxBindingRecyclerView = "com.jakewharton.rxbinding4:rxbinding-recyclerview:${Versions.rxBinding}"
    const val rxBindingSlidingPaneLayout =
        "com.jakewharton.rxbinding4:rxbinding-slidingpanelayout:${Versions.rxBinding}"
    const val rxBindingSwipeRefreshLayout =
        "com.jakewharton.rxbinding4:rxbinding-swiperefreshlayout:${Versions.rxBinding}"
    const val rxBindingMaterial = "com.jakewharton.rxbinding4:rxbinding-material:${Versions.rxBinding}"

    // Database
    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val roomRxJava = "androidx.room:room-rxjava3:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"

    // Koin
    const val koinAndroidScopes = "org.koin:koin-androidx-scope:${SharedVersions.koin}"
    const val koinAndroidViewModel = "org.koin:koin-androidx-viewmodel:${SharedVersions.koin}"

    // Google Firebase
    const val firebaseDatabase = "com.google.firebase:firebase-database:${Versions.firebaseDatabase}"
    const val firebaseStorage = "com.google.firebase:firebase-storage:${Versions.firebaseStorage}"

    // Utils
    const val crashlyticsNdk = "com.google.firebase:firebase-crashlytics-ndk:${Versions.crashlytics}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    const val leakCanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanary}"

    const val moshi = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
    const val moshiCodegen = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"

    // Android navigation
    const val navFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.navKtx}"
    const val navUiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navKtx}"

    // Android lifecycle
    const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRuntime}"
    const val lifecycleRuntimeExtensions = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleExtensions}"
    const val lifecycleViewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleViewModel}"
    const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleExtensions}"

    // UI
    const val appCompat = "androidx.appcompat:appcompat:${Versions.androidAppCompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideAnnotation = "com.github.bumptech.glide:compiler:${Versions.glide}"

    // Web
    const val browser = "androidx.browser:browser:${Versions.browser}"
}

object TestDependencies {
    private object Versions {

        const val androidxCoreTesting = "2.1.0"
        const val androidxTest = "1.2.0"
        const val equalsVerifier = "3.1.13"
        const val espresso = "3.3.0"
        const val junit = "1.1.1"
        const val junit5 = "5.4.2"
        const val mannodermausJunit5 = "1.2.0"
        const val mockk = "1.11.0"
        const val mockito = "3.9.0"
        const val kakao = "2.4.0"
    }

    const val androidxCoreTesting = "androidx.arch.core:core-testing:${Versions.androidxCoreTesting}"
    const val androidxTestCoreKtx = "androidx.test:core-ktx:${Versions.androidxTest}"
    const val androidxTestRules = "androidx.test:rules:${Versions.androidxTest}"
    const val androidxTestRunner = "androidx.test:runner:${Versions.androidxTest}"
    const val equalsVerifier = "nl.jqno.equalsverifier:equalsverifier:${Versions.equalsVerifier}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val espressoIntents = "androidx.test.espresso:espresso-intents:${Versions.espresso}"
    const val junit = "androidx.test.ext:junit:${Versions.junit}"
    const val jupiterApi = "org.junit.jupiter:junit-jupiter-api:${Versions.junit5}"
    const val jupiterEngine = "org.junit.jupiter:junit-jupiter-engine:${Versions.junit5}"
    const val jupiterParams = "org.junit.jupiter:junit-jupiter-params:${Versions.junit5}"
    const val koinTest = "org.koin:koin-test:${SharedVersions.koin}"
    const val mannodermausCore = "de.mannodermaus.junit5:android-test-core:${Versions.mannodermausJunit5}"
    const val mannodermausTestRunner = "de.mannodermaus.junit5:android-test-runner:${Versions.mannodermausJunit5}"
    const val mockk = "io.mockk:mockk:${Versions.mockk}"
    const val mockitoAndroid = "org.mockito:mockito-android:${Versions.mockito}"
    const val mockkAndroid = "io.mockk:mockk-android:${Versions.mockk}"
    const val kakao = "com.agoda.kakao:kakao:${Versions.kakao}"
}
