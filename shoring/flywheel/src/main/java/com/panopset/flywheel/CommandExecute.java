package com.panopset.flywheel;

import java.io.StringWriter;
import com.panopset.compat.Logop;

/**
 * <h1>e - Execute</h1>
 * <p>
 * Excecute any public static method, defined as a Java System property,
 * where the key starts with "com.panopset.flywheel.cmdkey." and finishes
 * with the name of the command to be used in the e command definition.
 * The function returned by the key must be one that returns a String and takes 0 or more
 * String parameters. If parameters match variable names, then they will be
 * replaced by the variable value, otherwise the parameter will be used as is.
 * </p>
 * <p>
 * You may register objects that will be available using the
 * Flywheel.Builder.registerObject function. The keys must be one word. Instance
 * methods may be called from registered objects.
 * </p>
 *
 * <pre>
 *
 * ${&#064;p name}panopset${&#064;q}
 * 
 * ${&#064;e capitalize(name)}
 *
 * </pre>
 * 
 * <p>
 * The above script will output:
 * </p>
 * 
 * <pre>
 *
 * Panopset
 *
 * </pre>
 */
public class CommandExecute extends TemplateDirectiveCommand {
  
  static final String CAPITALIZE_CMD = "${&#064; capitalize(name)}";
  public static Flywheel flywheel;

  /**
   * Short HTML text for publishing command format in an HTML document.
   *
   * @return <b>${&#064;e capitalize(name)}</b>.
   */
  public static String getShortHtmlText() {
    return CAPITALIZE_CMD;
  }

  public CommandExecute(final TemplateLine templateLine, final String innerPiece,
      final Template template) {
    super(templateLine, innerPiece, template);
  }

  @Override
  public final void resolve(final StringWriter sw) throws FlywheelException {
    flywheel = getTemplate().getFlywheel();
    try {
      String str = getParams();
      int incr = str.lastIndexOf(".");
      if (incr > -1) {
        String key = str.substring(0, incr);
        Object object = getTemplate().getFlywheel().getRegisteredObjects()
            .get(key);
        if (object != null) {
          sw.append(new ReflectionInvoker.Builder().object(object)
              .methodAndParms(str.substring(incr + 1))
              .mapProvider(getTemplate().getFlywheel()).construct().exec());
          return;
        }
      }
      String rtn = new ReflectionInvoker.Builder()
          .classMethodAndParms(getParams())
          .mapProvider(getTemplate().getFlywheel()).construct().exec();
      Logop.debug(String.format("%s %s: %s", getTemplate().getTemplateSource().getName(), getTemplateLine().toString(), rtn));
      sw.append(rtn);
    } catch (FlywheelException ex) {
      Logop.error(ex);
      if (getTemplate() != null) {
        Logop.warn(getTemplate().getRelativePath());
        if (getTemplate().getCommandFile() != null) {
          Logop.warn("Output file: " + getTemplate().getCommandFile());
          Logop.warn("source: " + getTemplateLine());
        }
      }
      if (getTemplate() != null && getTemplate().getFlywheel() != null) {
        getTemplate().getFlywheel().stop(ex.getMessage());
      }
      String errorMessage = String.format("Failure executing %s.", getTemplateLine());
      this.getTemplate().getFlywheel().stop(errorMessage);
    }
  }

}
