plugins {
    `maven-publish`
}

group = "com.zenith.location"
version = "1.0.2"

publishing {
    publications {
        create<MavenPublication>("release") {
            artifactId = "zlocation"
            artifact(layout.buildDirectory.file("outputs/aar/zlocation.aar")) {
                extension = "aar"
            }
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/fbu11/ZLocation")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: ""
                password = project.findProperty("gpr.key") as String? ?: ""
            }
        }
    }
}

tasks.register<Copy>("prepareAar") {
    from("libs/zlocation.aar")
    into(layout.buildDirectory.dir("outputs/aar"))
    rename { fileName -> "zlocation.aar" }
}

tasks.named("publish") {
    dependsOn("prepareAar")
}

// 显式指定 publishReleasePublicationToGitHubPackagesRepository 依赖 prepareAar
tasks.named("publishReleasePublicationToGitHubPackagesRepository") {
    dependsOn("prepareAar")
}
