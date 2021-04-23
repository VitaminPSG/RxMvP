// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val kotlin_version by extra("1.4.32")
    repositories {
        google()
        jcenter()
        mavenLocal()
        mavenCentral()
    }
    dependencies {
        classpath(Plugins.androidGradle)
        classpath(Plugins.kotlinGradle)
        classpath(Plugins.navigationSafeArgs)
        Plugins.configureGoogleServices {
            classpath(Plugins.googleServices)
        }
        classpath(Plugins.crashlytics)

        classpath(Plugins.junit5)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
    }
}


allprojects {
    repositories {
        google()
        jcenter()
        mavenLocal()
        mavenCentral()
    }
}
