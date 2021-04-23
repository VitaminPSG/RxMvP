import org.gradle.kotlin.dsl.`kotlin-dsl`

repositories {
    jcenter()
    google()
}

plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(gradleApi())
    implementation(localGroovy())
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}
