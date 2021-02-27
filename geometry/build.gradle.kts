dependencies {
    // kotlin
    implementation(Libs.kotlin_jdk8)
    implementation(Libs.kotlin_reflect)
    implementation(Libs.kotlin_stdlib)

    // test
    testImplementation(Libs.kotest_junit)
    testImplementation(Libs.junit_engine)
}