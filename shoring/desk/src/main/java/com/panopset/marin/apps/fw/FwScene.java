package com.panopset.marin.apps.fw;

import java.io.File;
import java.io.IOException;
import java.util.function.IntFunction;

import com.panopset.compat.Fileop;
import com.panopset.compat.Logop;
import com.panopset.compat.Stringop;
import com.panopset.flywheel.Command;
import com.panopset.flywheel.FlywheelBuilder;
import com.panopset.flywheel.FlywheelDebugContext;
import com.panopset.flywheel.FlywheelDebugStateIdle;
import com.panopset.flywheel.FlywheelDebugStateStepping;
import com.panopset.flywheel.FlywheelFunction;
import com.panopset.flywheel.FlywheelListDriver;
import com.panopset.flywheel.LineFeedRules;
import com.panopset.flywheel.ReflectionInvoker;
import com.panopset.flywheel.Template;
import com.panopset.fxapp.JavaFXapp;
import com.panopset.fxapp.PanDirSelectorFX;
import com.panopset.fxapp.PanFileSelectorFX;
import com.panopset.fxapp.SceneUpdater;
import com.panopset.marin.app.swiftwheel.FlywheelPrompterFX;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class FwScene extends SceneUpdater {

  LineFeedRules lineFeedRules;
  FlywheelListDriver report;
  String input;
  String template;
  String templateFile;
  String targetDir;
  String splitz;
  boolean lineBreaks;
  boolean listBreaks;
  String tokens;

  private void init() {
    Logop.clear();
    templateFile = fwFileselectController.inpfile.getText();
    targetDir = fwDirselectController.inpfile.getText();
    input = fwInput.getText();
    template = fwTemplate.getText();
    splitz = fwSplitz.getText();
    lineBreaks = fwLineBreaks.isSelected();
    listBreaks = fwListBreaks.isSelected();
    tokens = fwTokens.getText();
    lineFeedRules = new LineFeedRules(fwLineBreaks.isSelected(), fwListBreaks.isSelected());
    fwTemplate.getStylesheets().add(HLG);
  }

  @Override
  protected void doUpdate() {
    if (report != null &&

        fwFileselectController.inpfile.getText().equals(templateFile) &&

        fwDirselectController.inpfile.getText().equals(targetDir) &&

        fwInput.getText().equals(input) &&

        fwTemplate.getText().equals(template) &&

        fwSplitz.getText().equals(splitz) &&

        fwLineBreaks.isSelected() == lineBreaks &&

        fwListBreaks.isSelected() == listBreaks &&

        fwTokens.getText().equals(tokens)

    ) {
      return;
    }
    init();
    if (isListHandling()) {
      handleList();
    }
  }

  private void doStep() {
    getContext().doStep();
  }

  private void doRun() {
    getContext().doRun();
  }

  private boolean isReadyToProcessFile() {
    if (Stringop.isBlank(templateFile)) {
      Logop.warn("Please specify a template file.");
      return false;
    }
    if (Stringop.isBlank(targetDir)) {
      Logop.warn("Please specify a target directory.");
      return false;
    }
    return true;
  }

  private FlywheelDebugContext fwdc;

  private FlywheelDebugContext getContext() {
    if (fwdc == null) {
      fwdc = new FlywheelDebugContext() {

        @Override
        public void initiateRun() {
          init();
          if (!isReadyToProcessFile()) {
            reset();
            return;
          }
          Platform.runLater(() -> fwRun.setDisable(true));
          File targetDirectory = new File(targetDir);
          if (!targetDirectory.isDirectory()) {
            Logop.warn(String.format("%s is not a directory path.", targetDir));
            reset();
            return;
          }
          if (!targetDirectory.canWrite()) {
            Logop.warn(String.format("Can not write to %s, please check permissions and existence.",
                targetDir));
            reset();
            return;
          }
          File topTemplateFile = new File(templateFile);
          if (!topTemplateFile.isFile() || !topTemplateFile.canRead()) {
            Logop.warn(String.format("Can not read %s, please check permissions and existence.",
                templateFile));
            reset();
            return;
          }
          FlywheelBuilder fb = new FlywheelBuilder();
          Platform.runLater(() -> fwTemplate.setText(Fileop.readTextFile(topTemplateFile)));
          fb.file(topTemplateFile);
          fb.targetDirectory(targetDirectory);
          setFlywheel(fb.construct());
          getFlywheel().getControl().setFlywheelDebugContext(this);
          saveClassLoaders(getClass().getClassLoader());
          File tf = new File(fwFileselectController.inpfile.getText());
          new Thread(() -> {
            String s = getFlywheel().exec();
            Platform.runLater(() -> {
              fwTemplate.setText(Fileop.readTextFile(tf));
              fwOutput.setText(s);
            });
            reset();
          }, String.format("Flywheel %s", tf.getName())).start();
        }

        @Override
        public void showTemplate(Template t) {
          if (getState() instanceof FlywheelDebugStateStepping) {
            Platform.runLater(() -> fwOutput.setText(""));
            Platform.runLater(() -> fwTemplate.setText(t.getTemplateSource().getRaw()));
            Platform.runLater(() -> fwTemplate.selectRange(0, fwTemplate.getText().length()));
            pause();
          }
        }

        @Override
        public void showTopCommand(Template t, Command c) {
          if (getState() instanceof FlywheelDebugStateStepping) {
            Platform.runLater(() -> fwOutput.setText(""));
            
            
            int startHilite = c.getTemplateLine().getTemplateCharIndex();

            Platform.runLater(() -> fwTemplate.selectRange(startHilite, c.getTemplateLine().getLine().length()));
            
            
            
            pause();
          }
        }

        @Override
        public void showResult(String result) {
          if (getState() instanceof FlywheelDebugStateStepping) {
            Platform.runLater(() -> fwOutput.setText(result));
            pause();
          }
        }

        private void pause() {
          JavaFXapp.getZombie().addStopAction(quickRelease);
          fwRun.setDisable(false);
          getFlywheel().pause();
        }

        @Override
        public void cleanup() {
          if (getFlywheel() != null) {
            getFlywheel().releasePause();
          }
          JavaFXapp.getZombie().removeStopAction(quickRelease);
          fwRun.setDisable(false);
        }

        private final Runnable quickRelease = () -> getFlywheel().releasePause();
      };
    }
    return fwdc;
  }

  private static final String HLG = FwScene.class.getResource("/com/panopset/marin/fx/hlg.css").toExternalForm(); 
  private static final String HLR = FwScene.class.getResource("/com/panopset/marin/fx/hlr.css").toExternalForm(); 
  private int i = 0;

  private void saveClassLoaders(ClassLoader classLoader) {
    if (classLoader == null) {
      return;
    }
    if (!ReflectionInvoker.CLASSLOADERS.contains(classLoader)) {
      ReflectionInvoker.CLASSLOADERS.add(classLoader);
    }
    if (i++ > 100) {
      Logop.error("Breaking loop.");
    }
    saveClassLoaders(classLoader.getParent());
  }

  private void handleList() {
    String wrktxt = fwOutput.getText();
    fwOutput.setText(wrktxt.replace("\n", ""));
    try {
      report = new FlywheelListDriver.Builder(

          Stringop.stringToList(fwInput.getText()), fwTemplate.getText())
              .withLineFeedRules(lineFeedRules).withSplitz(fwSplitz.getText())
              .withTokens(fwTokens.getText())

              .build();
      String result = report.getOutput();
      fwOutput.setText(result);
    } catch (IOException e) {
      Logop.error(e.getMessage());
    }
    // getPrompterFX().setFlywheel(report.getFlywheel(), fw_prompters, fw_input, fw_template);
    if (!lineBreaks) {
      Logop.warn("Line breaks not checked, so output will be on one line.");
    }
  }

  private boolean isListHandling() {
    return Stringop.isBlank(fwFileselectController.inpfile.getText())
        && Stringop.isBlank(fwDirselectController.inpfile.getText());
  }

  private void doClear() {
    fwInput.setText("");
    fwTemplate.setText("");
    cbSamples.getSelectionModel().select(0);
    cbFunctions.getSelectionModel().select(0);
  }

  private void doClearAll() {
    doClear();
    fwFileselectController.inpfile.setText("");
    fwDirselectController.inpfile.setText("");
  }

  FlywheelPrompterFX fwp;

  private FlywheelPrompterFX getPrompterFX() {
    if (fwp == null) {
      fwp = new FlywheelPrompterFX();
    }
    return fwp;
  }

  private void triggerUpdate() {
    if (getContext().getState() instanceof FlywheelDebugStateIdle) {
      triggerAnUpdate();
    }
  }

  @FXML
  private void initialize() {
    IntFunction<String> format = (digits -> " %" + digits + "d ");
    //fwTemplate.setParagraphGraphicFactory(LineNumberFactory.get(fwTemplate, format));
    fwInput.textProperty().addListener((observable, oldValue, newValue) -> triggerUpdate());
    fwTemplate.textProperty().addListener((observable, oldValue, newValue) -> triggerUpdate());
    fwTokens.textProperty().addListener((observable, oldValue, newValue) -> triggerUpdate());
    fwSplitz.textProperty().addListener((observable, oldValue, newValue) -> triggerUpdate());
    fwLineBreaks.setOnAction(event -> triggerUpdate());
    fwListBreaks.setOnAction(event -> triggerUpdate());
    fwClear.setOnAction(event -> doClear());
    fwClearAll.setOnAction(event -> doClearAll());
    fwStep.setOnAction(event -> doStep());
    fwRun.setOnAction(event -> doRun());
    new FxSampleLoader().loadUpSamplesComboBox(cbSamples, fwInput, fwTemplate, fwLineBreaks,
        fwListBreaks, fwTokens, fwSplitz);
    new FxFunctionLoader().loadUpFunctions(cbFunctions, fwTemplate);
  }

  @FXML
  HBox fwFileselect;

  @FXML
  HBox fwDirselect;

  @FXML
  PanFileSelectorFX fwFileselectController;

  @FXML
  PanDirSelectorFX fwDirselectController;

  @FXML
  Button fwStep;

  @FXML
  Button fwRun;

  @FXML
  HBox fwPrompters;

  @FXML
  TextArea fwInput;

  @FXML
  TextArea fwTemplate;

  @FXML
  TextArea fwOutput;

  @FXML
  TextField fwTokens;

  @FXML
  TextField fwSplitz;

  @FXML
  CheckBox fwLineBreaks;

  @FXML
  CheckBox fwListBreaks;

  @FXML
  Button fwClear;

  @FXML
  Button fwClearAll;

  @FXML
  ComboBox<String> cbSamples;

  @FXML
  ComboBox<FlywheelFunction> cbFunctions;

}
