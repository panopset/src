[home](../../README.md)

Create, in your home directory, a file called setup.cmd.
Naturally there are many ways to set up your environment,
this is just one.


    set JAVA_HOME=%USERPROFILE%\apps\jdk
    set MAVEN_HOME=%USERPROFILE%\apps\mvn
    set ANT_HOME=%USERPROFILE%\apps\ant
    set PATH=%PATH%;^
    %MAVEN_HOME%\bin;^
    %ANT_HOME%\bin;^
    %JAVA_HOME%\bin
