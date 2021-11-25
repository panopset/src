call arch\win\readProps deploy.properties
set HTTP_HOME=%HTTP_HOME:/=\%
call arch\win\readProps deploy_win.properties
call arch\win\readProps %USERPROFILE%\.com.panopset.deploy.properties
