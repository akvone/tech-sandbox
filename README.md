# Why does this project exist?

The primary purpose of the `sandbox` project is to give a starting point with an existing code base to play with
different frameworks, libraries, and technologies commonly used in Java / Kotlin enterprise development.

# Prerequisites

* Maven
* JDK v8 (v11 for some modules)

Installed Docker is preferable but is not required

# Credentials

Most services use `sandbox`/`sandbox` as user/password pairs

# Modules

## Sandbox modules:

* [kotlin sandbox](sandboxes/kotlin)
* [spring-boot sandbox](sandboxes/spring-boot)
* [spring-boot-postgresql sandbox](sandboxes/spring-boot-postgresql)
* [spring-boot-security sandbox](sandboxes/spring-boot-security)
* [spring-cloud sandbox](sandboxes/spring-cloud)
* [spring-cloud-gateway sandbox](sandboxes/spring-cloud-gateway)

## Other modules

* infrastructure - useful scripts and docker-compose.yaml files
* maven-parents - sandbox modules use them
* shared
    * shared-resources
    * shared-utils

# Windows automatizations

## Minikube (local kubernetes)

To set up everything, execute:

1. Run [Install winget](https://docs.microsoft.com/en-us/windows/package-manager/winget/) if needed
2. Run `.\infrastructure\minikube\install-or-upgrade-all.ps1` (May require restarting terminal)
3. Run in 1st terminal
    * Run `.\infrastructure\minikube\start.ps1`
4. Run in 2nd terminal
    * `minikube tunnel`
5. Run in 3rd terminal
    * `helm dependency update .\infrastructure\helm\sandbox-chart`
    * `helm install sandbox .\infrastructure\helm\sandbox-chart`
6. Go to http://localhost

To stop minikube, execute:

1. Run `.\infrastructure\minikube\stop.ps1`

