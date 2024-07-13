plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        desktopMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.kmidi)
            implementation(libs.bundles.voyager)
            implementation(compose.desktop.currentOs)
            implementation(libs.mpfilepicker)
        }
    }
}

compose.desktop {
    application {
        mainClass = "org.wysko.inspektmidi.MainKt"
    }
}
