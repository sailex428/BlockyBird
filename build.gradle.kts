plugins {
    id("fabric-loom") version "1.12-SNAPSHOT"
    id("maven-publish")
}

version = property("mod_version").toString()
group = property("maven_group").toString()
val mcVersion = property("mc_version").toString()
val yarnMappings = property("yarn_mappings").toString()
val loaderVersion = property("loader_version").toString()
val fabricVersion = property("fabric_version").toString()
val baseName = property("archives_base_name").toString()

base {
    archivesName = baseName
}

repositories {

}

dependencies {
    minecraft("com.mojang:minecraft:$mcVersion")
    mappings("net.fabricmc:yarn:$yarnMappings:v2")
    modImplementation("net.fabricmc:fabric-loader:$loaderVersion")
    modImplementation("net.fabricmc.fabric-api:fabric-api:$fabricVersion")
}

tasks.processResources {
    inputs.property("version", version)
    inputs.property("minecraft_version", mcVersion)
    inputs.property("loader_version", loaderVersion)
    filteringCharset = "UTF-8"

    filesMatching("fabric.mod.json") {
        expand("version" to version,
                "minecraft_version" to mcVersion,
                "loader_version" to loaderVersion)
    }
}

java {
    withSourcesJar()
    val javaVersion = JavaVersion.toVersion("21")
    targetCompatibility = javaVersion
    sourceCompatibility = javaVersion
}

tasks.jar {
    from("LICENSE") {
        rename { "${it}_${baseName}" }
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = group.toString()
            artifactId = baseName
            version = "$version+$mcVersion"

            artifact(tasks.remapJar.get().archiveFile)
            artifact(tasks.remapSourcesJar.get().archiveFile) {
                classifier = "sources"
            }
        }
    }
}
