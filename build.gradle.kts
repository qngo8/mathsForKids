plugins {
    id("com.android.application") version "8.5.0" apply false
    id("com.android.library") version "8.5.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.23" apply false
}

buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.2") // ✅ add this
    }
}
