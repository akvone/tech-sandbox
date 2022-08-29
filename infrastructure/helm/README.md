# Commands
Install: `helm install sandbox .\infrastructure\helm\sandbox-chart`

Upgrade: `helm upgrade sandbox .\infrastructure\helm\sandbox-chart`

Uninstall `helm uninstall sandbox`

# Notes for developing:
* Helm chart for grafana doesn't have a default admin password out of the box. [It's generated](https://github.com/helm/charts/issues/15708). The default username is `admin`