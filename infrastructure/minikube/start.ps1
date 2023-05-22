$env:PATH = [System.Environment]::GetEnvironmentVariable("PATH", "Machine") + [System.Environment]::GetEnvironmentVariable("PATH", "User")  # Refreshes PATH variable

docker version --format '{{.Server.Os}}-{{.Server.Version}}'
if (!($?)){
  echo "Start Docker Desktop"
  start "C:\Program Files\Docker\Docker\Docker Desktop.exe"
}

docker version --format '{{.Server.Os}}-{{.Server.Version}}'
while (!($?)){
  echo "Waiting for Docker Desktop to become ready"
  Start-Sleep -Seconds 1
  docker version --format '{{.Server.Os}}-{{.Server.Version}}'
}

echo "Docker is ready"

# Read https://minikube.sigs.k8s.io/docs/start/
minikube start
minikube kubectl
minikube addons enable ingress
minikube dashboard