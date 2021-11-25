home](../../README.md)

Linux prerequisites.

    sudo apt-get -y install vim maven ant git nginx
    sudo chown -R $USER /var/www
    
Get the latest jdk [here](https://jdk.java.net/16/), and update your path to use it.  Since you are getting the latest Java, you'll also need the latest [maven](https://maven.apache.org/).
    
    # These you will want to adjust to match your system setup, 
    JAVA_HOME=~/apps/jdk-16.0.1
    MAVEN_HOME=~/apps/mvn
    PATH=$JAVA_HOME/bin:$PATH:$MAVEN_HOME/bin
