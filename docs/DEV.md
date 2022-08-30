# Notes

* The documentation contains mermaid
  sections. [See more](https://github.blog/2022-02-14-include-diagrams-markdown-files-mermaid/)

# Useful commands

## Kubernetes

* Expose service to make it available externally
  `kubectl expose deployment <<serviceName>> --type=LoadBalancer --port=<<portNumber>> --target-port=main`