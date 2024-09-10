@echo off
rem Build application for Windows.
call readProps.cmd appwin.properties
call readProps.cmd deploy.properties
call mvn -f shoring clean install
call mvn -f legacy clean install
call lw.cmd

rem Copy the all-in-one jar to the home directory.
copy /b/v/y legacy\target\panopset-jar-with-dependencies.jar %USERPROFILE%\panopset.jar
