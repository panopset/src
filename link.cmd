call loadprops.cmd
set R=%USERPROFILE%\.m2\repository

rem update link.sh, linkmac.sh, and shoring/pom.xml fx.v property as well.
set MP=^
%R%/org/openjfx/javafx-base/%FXV%/javafx-base-%FXV%-%FX_ARCH%.jar;^
%R%/org/openjfx/javafx-graphics/%FXV%/javafx-graphics-%FXV%-%FX_ARCH%.jar;^
%R%/org/openjfx/javafx-controls/%FXV%/javafx-controls-%FXV%-%FX_ARCH%.jar;^
%R%/org/openjfx/javafx-fxml/%FXV%/javafx-fxml-%FXV%-%FX_ARCH%.jar;^
%R%/org/openjfx/javafx-web/%FXV%/javafx-web-%FXV%-%FX_ARCH%.jar;^
%R%/org/openjfx/javafx-swing/%FXV%/javafx-swing-%FXV%-%FX_ARCH%.jar;^
%R%/org/openjfx/javafx-media/%FXV%/javafx-media-%FXV%-%FX_ARCH%.jar;^
%R%/com/google/code/gson/gson/%GSONV%/gson-%GSONV%.jar;^
%R%/com/panopset/compat/%PV%/compat-%PV%.jar;^
%R%/com/panopset/desk/%PV%/desk-%PV%.jar;^
%R%/com/panopset/blackjackEngine/%PV%/blackjackEngine-%PV%.jar;^
%R%/com/panopset/flywheel/%PV%/flywheel-%PV%.jar;^
%R%/com/panopset/lowerclass/%PV%/lowerclass-%PV%.jar;^
%R%/com/panopset/fxapp/%PV%/fxapp-%PV%.jar;^
%R%/com/panopset/joist/%PV%/joist-%PV%.jar

jpackage ^
  -n panopset ^
  -p %JAVA_HOME%\jmods;%MP% ^
  -m com.panopset.joist/com.panopset.joist.Flywheel ^
  --vendor "Panopset" ^
  --copyright "1996-2021 Karl Dinwiddie" ^
  --license-file LICENSE ^
  --description "Panopset desktop applications." ^
  --add-launcher flywheel=launchers/flywheel.properties ^
  --add-launcher blackjack=launchers/blackjack.properties ^
  --add-launcher checksum=launchers/checksum.properties ^
  --add-launcher scrambler=launchers/scrambler.properties ^
  --add-launcher lowerclass=launchers/lowerclass.properties ^
  --app-version %PV% ^
  --dest target ^
  --win-dir-chooser ^
  --type msi

