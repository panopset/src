[home](../../README.md)

Append to ~/.zshrc:
    
    # These you will want to adjust to match your system setup.
    export JAVA_HOME=~/apps/jdk-14.0.2.jdk/Contents/Home
    export MAVEN_HOME=~/apps/apache-maven-3.6.3

For eclipse [create](https://stackoverflow.com/questions/829749) a launcher that will pick up the environment variables:

    alias e='open ~/eclipse/java-2020-09/Eclipse.app'
