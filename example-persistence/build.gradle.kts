plugins {
    id("java")
    id("io.spring.dependency-management") version "1.1.7"
}

group = "ch.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

// Import Spring Boot BOM for dependency management
dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:4.0.0")
    }
}

dependencies {
    // Depend on domain layer
    implementation(project(":example-business"))

    // Persistence adapter dependencies
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.h2database:h2")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}