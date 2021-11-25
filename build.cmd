call loadprops.cmd
echo removing %HTTP_HOME%
rmdir /s /q %HTTP_HOME%
echo removing target
rmdir /s /q target
call mvn -f shoring clean install
echo 'Linking, this will take a minute...'
call link.cmd
xcopy target\*.msi %HTTP_HOME%\installers\
echo Deploying to %HTTP_HOME%
ant
