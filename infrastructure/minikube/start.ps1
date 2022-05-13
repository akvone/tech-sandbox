get-process "Docker Desktop" > $null 2>&1
if ($?){
  echo "Docker is running"
} else {
  echo "Start Docker Desktop"
  start "C:\Program Files\Docker\Docker\Docker Desktop.exe"
  Start-Sleep -Seconds 30
}

# Read https://minikube.sigs.k8s.io/docs/start/
minikube start
minikube kubectl
minikube dashboard
minikube tunnel