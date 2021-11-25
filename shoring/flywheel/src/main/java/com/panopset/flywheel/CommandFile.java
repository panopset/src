package com.panopset.flywheel;

import java.io.File;
import java.io.StringWriter;
import com.panopset.compat.Fileop;
import com.panopset.compat.Stringop;


/**
 * <h1>f - File</h1>
 *
 * <p>
 * Example 1
 * </p>
 *
 * <pre>
 * ${&#064;f someFile.txt}
 * </pre>
 *
 * <p>
 * Example 2
 * </p>
 *
 * <pre>
 * ${&#064;p fileName}someFile.txt${&#064;q}
 * ${&#064;f &#064;fileame}
 * </pre>
 * <p>
 * Output to the specified file, until the end of the template or another file command is found.
 * </p>
 *
 */
public final class CommandFile extends MatchableCommand {

  /**
   * Short HTML text for publishing command format in an HTML document.
   *
   * @return <b>${&#064;f somefile.txt}</b>.
   */
  public static String getShortHtmlText() {
    return "${&#064;f somefile.txt}";
  }

  public CommandFile(final TemplateLine templateLine, final String innerPiece, final Template template) {
    super(templateLine, innerPiece, template);
  }

  @Override
  public void resolve(final StringWriter sw) throws FlywheelException {
    if (getParams() == null) {
      return;
    }
    if (getTemplate().getFlywheel().isOutputEnabled()) {
      StringWriter newStringWriter = new StringWriter();
      getTemplate().getFlywheel().setWriter(newStringWriter);
      resolveMatchedCommands(newStringWriter);
      String fileName = getParams();
      String replacementFileName = getTemplate().getFlywheel().get(fileName);
      if (Stringop.isPopulated(replacementFileName)) {
        fileName = replacementFileName;
      }
      String outputFilePath =
          String.format("%s/%s", getTemplate().getFlywheel().getTargetDirectory(), fileName);
      Fileop.write(newStringWriter.toString(), new File(outputFilePath));
    } else {
      resolveMatchedCommands(sw);
    }
  }
}
