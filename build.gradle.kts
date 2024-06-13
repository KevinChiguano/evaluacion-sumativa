plugins {
    id("java")
    id("application")
}

group = "com.distribuida"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // CDI - OpenWebBeans
    implementation("org.apache.openwebbeans:openwebbeans-impl:4.0.2")
    //implementation("org.apache.openwebbeans:openwebbeans-spi:4.0.2")
    implementation("org.apache.openwebbeans:openwebbeans-se:4.0.2")


    // Especificaciones necesarias
    //implementation("org.apache.geronimo.specs:geronimo-atinject_1.0_spec:1.1")
    //implementation("org.apache.geronimo.specs:geronimo-interceptor_1.2_spec:1.0")
    //implementation("org.apache.geronimo.specs:geronimo-annotation_1.3_spec:1.0")
    //implementation("org.apache.geronimo.specs:geronimo-jcdi_2.0_spec:1.0")

    // JPA - EclipseLink
    implementation("org.eclipse.persistence:eclipselink:4.0.3")
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")

    // REST - Helidon WebServer

    implementation("io.helidon.webserver:helidon-webserver:4.0.9")

    // JSON
    implementation("com.google.code.gson:gson:2.11.0")

    // SQLite
    implementation("org.xerial:sqlite-jdbc:3.46.0.0")

    // https://mvnrepository.com/artifact/org.apache.deltaspike.core/deltaspike-core-api
    //implementation("org.apache.deltaspike.core:deltaspike-core-api:1.9.5")

    //implementation("org.apache.deltaspike.core:deltaspike-core-impl:2.0.0")
    //implementation("org.apache.deltaspike.cdictrl:deltaspike-cdictrl-api:2.0.0")
    //implementation("org.apache.deltaspike.cdictrl:deltaspike-cdictrl-weld:2.0.0")
    //implementation("org.jboss.weld.se:weld-se-core:5.1.2.Final")

}

tasks.jar {
    manifest {
        attributes(
            mapOf("Main-Class" to "com.distribuida.Main",
                "Class-Path" to configurations.runtimeClasspath
                    .get()
                    .joinToString(separator = " ") { file ->
                        "${file.name}"
                    })
        )
    }
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}