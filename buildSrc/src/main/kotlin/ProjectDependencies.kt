object LibVers {
    const val junit = "5.6.0"
    const val kotest = "4.4.1"
    const val arrow = "0.11.0"
    const val log4j = "2.12.0"
}

object BuildLibs {
    const val shadow = "com.github.jengelman.gradle.plugins:shadow:${PluginVers.shadow}"
}

object Libs {

    // Kotlin
    const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Global.kotlin_version}"
    const val kotlin_jdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Global.kotlin_version}"
    const val kotlin_reflect = "org.jetbrains.kotlin:kotlin-reflect:${Global.kotlin_version}"
    const val arrow = "io.arrow-kt:arrow-core:${LibVers.arrow}"

    // Logging
    const val log4j = "org.apache.logging.log4j:log4j-api:${LibVers.log4j}"
    const val log4j_core = "org.apache.logging.log4j:log4j-core:${LibVers.log4j}"
    const val log4j_kotlin = "org.apache.logging.log4j:log4j-api-kotlin:1.0.0"
    const val slf4j_over_log4j = "org.apache.logging.log4j:log4j-slf4j-impl:${LibVers.log4j}"

    // Tests
    const val junit_params = "org.junit.jupiter:junit-jupiter-params:${LibVers.junit}"
    const val junit_engine = "org.junit.jupiter:junit-jupiter-engine:${LibVers.junit}"
    const val kotest_junit = "io.kotest:kotest-runner-junit5:${LibVers.kotest}"
    const val kotest_arrow = "io.kotest:kotest-assertions-arrow:${LibVers.kotest}"
}

object PluginVers {
    const val kotlin = Global.kotlin_version
    const val detekt = "1.15.0"
    const val detekt_formatting = detekt
    const val update_dependencies = "0.36.0"
    const val shadow = "6.1.0"
}

object Plugins {
    const val kotlin = "org.jetbrains.kotlin.jvm"
    const val detekt = "io.gitlab.arturbosch.detekt"
    const val detekt_formatting = "io.gitlab.arturbosch.detekt:detekt-formatting"
    const val update_dependencies = "com.github.ben-manes.versions"
    const val shadow = "com.github.johnrengelman.shadow"
}
