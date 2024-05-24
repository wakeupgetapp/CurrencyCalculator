pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "CurrencyCalculator"
include(":app")
include(":core:database:internal")
include(":core:datastore:internal")
include(":core:network:internal")
include(":core:network:public")
include(":core:model")
include(":feature:calculator")
include(":feature:selector")
include(":core:ui")
include(":core:domain")
include(":core:data")
include(":core:data_test")
