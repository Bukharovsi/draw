dependencies {

    // project
    implementation(project(":geometry"))
    implementation(project(":drawing"))

    // kotlin
    implementation(Libs.kotlin_jdk8)
    implementation(Libs.kotlin_reflect)
    implementation(Libs.kotlin_stdlib)

    // arrow
    implementation(Libs.arrow)

    // logging
    implementation(Libs.log4j_kotlin)
    implementation(Libs.log4j_core)

    // test
    testImplementation(Libs.kotest_junit)
    testImplementation(Libs.kotest_arrow)
    testImplementation(Libs.junit_engine)
    testImplementation(Libs.junit_params)
}