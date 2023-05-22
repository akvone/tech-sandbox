helm version > $null 2>&1
if ($?){
  echo "Helm is installed. Upgrading it"
  winget upgrade Helm.Helm
} else {
  echo "Helm is not installed. Installing it"
  winget install Helm.Helm
  echo "Helm is installed, but most probably PATH was updated incorrectly. Please fix it manually" # TODO: fix it somehow
}

docker -v > $null 2>&1
if ($?){
  echo "Docker is installed. Upgrading it"
  winget upgrade "Docker Desktop"
} else {
  echo "Docker is not installed. Installing it"
  winget install "Docker Desktop"
}

minikube version > $null 2>&1
if ($?){
  echo "Minikube is installed. Upgrading it"
  winget upgrade minikube
} else {
  echo "Minikube is not installed. Installing it"
  winget install minikube
}
