plugins {
    id("java-library")
    id("net.labymod.labygradle")
    id("net.labymod.labygradle.addon")
}

group = "top.einsluca"
version = "1.0.0"

java.toolchain.languageVersion.set(JavaLanguageVersion.of(21))
val versions = providers.gradleProperty("net.labymod.minecraft-versions").get().split(";")


labyMod {
    defaultPackageName = "top.einsluca.autogg" //change this to your main package name (used by all modules)

    minecraft {
        registerVersion(versions.toTypedArray()) {
            runs {
                getByName("client") {
                    // When the property is set to true, you can log in with a Minecraft account
                    // devLogin = true
                }
            }
        }
    }

    addonInfo {
        namespace = "auto-gg"
        displayName = "AutoGG"
        author = "EinsLuca,huanli233"
        description = "Make yourself more likeable by automatically writing a GG in the chat after each round."
        minecraftVersion = "*"
        version = System.getenv().getOrDefault("VERSION", "1.0.0")
    }


}

subprojects {
    plugins.apply("net.labymod.labygradle")
    plugins.apply("net.labymod.labygradle.addon")

    group = rootProject.group
    version = rootProject.version
}
