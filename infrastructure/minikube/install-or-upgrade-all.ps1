docker -v > $null 2>&1
if ($?){
  echo "Docker is installed"
  winget upgrade "Docker Desktop"
} else {
  echo "Docker is not installed"
  winget install "Docker Desktop"
}

minikube version > $null 2>&1
if ($?){
  echo "Minikube is installed"
  winget upgrade minikube
} else {
  echo "Minikube is not installed"
  winget install minikube
}

helm version > $null 2>&1
if ($?){
  echo "Helm is installed"
} else {
  echo "Helm is not installed"
}
echo "Helm installation/upgrade is not supported. Do it manually: https://helm.sh/docs/intro/install/"
