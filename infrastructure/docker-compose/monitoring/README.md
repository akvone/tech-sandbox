# docker-compose.yaml for InfluxDB + Grafana + Chronograf (InfluxDB admin UI)
To start the app:
```
docker-compose up
```
To stop the app:
```
docker-compose down
```

## InfluxDB
No additional configuration is required

## Grafana
1. Use `admin`/`admin` as initial login/password
2. To add InfluxDB datasource use :
   1. `http://influxdb:8086` URL
   2. `sandbox` Database
   3. `sandbox` User
   4. `sandbox` Password

## Chronograf
1. To add InfluxDB connection use: 
    1. `http://influxdb:8086` Connection URL
    3. `sandbox` Username
    4. `sandbox` Password

# Usages:
1. [spring-boot](../../../sandboxes/spring-boot/README.md#metrics-for-resilience4j)
