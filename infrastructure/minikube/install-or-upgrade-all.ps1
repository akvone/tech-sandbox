helm version > $null 2>&1
if ($?){
  echo "Helm is installed"
  winget upgrade Helm.Helm --version 3.11.3
} else {
  echo "Helm is not installed. Installing it"
  winget install Helm.Helm
  echo "Helm is installed, but most probably PATH was updated incorrectly. Please fix it manually" # TODO: fix it somehow
}

docker -v > $null 2>&1
if ($?){
  echo "Docker is installed"
#  winget upgrade "Docker Desktop"
} else {
  echo "Docker is not installed. Installing it"
  winget install "Docker Desktop" --version 4.19.0
}

minikube version > $null 2>&1
if ($?){
  echo "Minikube is installed"
#  winget upgrade minikube
} else {
  echo "Minikube is not installed. Installing it"
  winget install minikube --version 1.30.1
}
