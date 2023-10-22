plugins {
    kotlin("jvm") version "1.8.10"
}

repositories { mavenCentral() }

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.10.0")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.10.0")

    testImplementation ("org.seleniumhq.selenium:selenium-java:4.14.0")

    implementation("io.github.bonigarcia:webdrivermanager:5.5.3")
}

tasks.withType(Test::class) {
    ignoreFailures = true
    useJUnitPlatform {}

    testLogging {
        showStandardStreams = true
    }
}
