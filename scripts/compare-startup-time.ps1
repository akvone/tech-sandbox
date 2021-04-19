# This script compares time for .jar startup and unpacked .jar startup
# IMPORTANT: Before execution add `.close()` to finish SpringBoot process right after startup
Set-Variable -Name "initialFolder" -Value "$PWD"
Set-Variable -Name "moduleName" -Value "spring-boot"
Set-Variable -Name "projectFolder" -Value "../$moduleName"
Set-Variable -Name "extractedJarFolder" -Value "extracted"
# ...
cd $projectFolder
mvn clean package
cd target
mkdir $extractedJarFolder
cd $extractedJarFolder
jar xf ../$moduleName-1.0-SNAPSHOT.jar
cd ..
# Measure time
echo "Starting .jar version"
Measure-Command {java -jar .\spring-boot-1.0-SNAPSHOT.jar}
echo "Starting unpacked .jar version"
cd $extractedJarFolder
Measure-Command {java org.springframework.boot.loader.JarLauncher}
# Return to initial folder
cd $initialFolder