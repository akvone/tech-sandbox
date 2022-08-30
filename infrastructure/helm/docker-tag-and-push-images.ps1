$components =
    "spring-boot",
    "spring-boot-admin",
    "spring-boot-postgresql",
    "spring-boot-security",
    "spring-cloud",
    "spring-cloud-gateway",
    "traffic-generator"
Foreach ($component in $components)
{
    echo "Pushing $component"
    docker tag "$($component):1.0-SNAPSHOT" "akvone/sandbox-$($component):1.0-SNAPSHOT"
    docker push "akvone/sandbox-$($component):1.0-SNAPSHOT"
}