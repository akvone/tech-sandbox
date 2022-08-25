# Useful commands
## Kubernetes
* Expose service to make it available externally 
  `kubectl expose deployment <<serviceName>> --type=LoadBalancer --port=<<portNumber>> --target-port=main`