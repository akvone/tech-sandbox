# Module structure

Generally, this module sticks to a rule: `one feature - one folder`.

For example there is a `src/main/resources/config/properties` folder which contains properties for code
inside `src/main/kotlin/com/akvone/properties` package 

# Features:
## Metrics for Resilience4j

![Resilience4j dashboard](docs/resilience4j-dashboard.png)

Steps:
1. Start the app in [`monitoring`](../../infrastructure/monitoring) module
1. Start the app in current module
1. Import all dashboards from [`monitoring`](monitoring) folder 
1. Configure event generation parameters with `POST localhost:8080/metrics/event-generator/state` (See `EventGeneratorParameters.kt`)
