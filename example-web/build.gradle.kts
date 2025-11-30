plugins {
    id("java")
    id("io.spring.dependency-management") version "1.1.7"
    id("org.openapi.generator") version "7.10.0"
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
    // Depend on domain and application layers
    implementation(project(":example-business"))
    implementation(project(":example-service"))

    // Web adapter dependencies
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // OpenAPI dependencies
    implementation("org.openapitools:jackson-databind-nullable:0.2.6")
    implementation("io.swagger.core.v3:swagger-annotations:2.2.20")

    // MapStruct
    implementation("org.mapstruct:mapstruct:1.6.3")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

// OpenAPI Generator configuration
tasks.register<org.openapitools.generator.gradle.plugin.tasks.GenerateTask>("generateApi") {
    generatorName.set("spring")
    inputSpec.set("$rootDir/example-web/src/main/resources/payment-api.yaml")
    outputDir.set("$buildDir/generated")
    apiPackage.set("ch.example.web.api")
    modelPackage.set("ch.example.web.dto")
    configOptions.set(mapOf(
        "interfaceOnly" to "true",
        "useSpringBoot3" to "true",
        "useTags" to "true",
        "skipDefaultInterface" to "true",
        "useJakartaEe" to "true"
    ))
}

// Add generated sources to compilation
sourceSets {
    main {
        java {
            srcDir("$buildDir/generated/src/main/java")
        }
    }
}

// Ensure API is generated before compilation
tasks.named("compileJava") {
    dependsOn("generateApi")
}

tasks.test {
    useJUnitPlatform()
}