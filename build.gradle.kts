// Root build.gradle.kts
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android)    apply false
    alias(libs.plugins.kotlin.compose)    apply false

    // declaras el plugin de Google Services aquí, sin versiones en otro lugar:
    id("com.google.gms.google-services") version "4.3.15" apply false
}
