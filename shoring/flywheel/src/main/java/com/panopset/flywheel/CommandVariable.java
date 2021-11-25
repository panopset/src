package com.panopset.flywheel;

import java.io.StringWriter;
import java.util.List;
import com.panopset.compat.Stringop;

/**
 * <h1>Variable</h1> There is no command associated with a variable, so you drop the <b>&#064;</b>
 * directive indicator, and then you specify a variable just as you would in any ant script or unix
 * shell. The variable must have been defined either in a map provided to the script through
 * Flywheel.Builder.mergeMap, or a Push command. Please do not define numbers as variable names, as
 * they are used in list processing.
 *
 * <pre>
 * ${variableName}
 * 
 * Flywheel pre-defined variables:
 * com.panopset.flywheel.template
 *
 * </pre>
 *
 */
public final class CommandVariable extends TemplateCommand {

  /**
   * Short HTML text for publishing command format in an HTML document.
   *
   * @return <b>${variableName}</b>.
   */
  public static String getShortHtmlText() {
    return "${variableName}";
  }

  public CommandVariable(final TemplateLine templateLine, final String innerPiece, final Template template) {
    super(templateLine, innerPiece, template);
    setParams(innerPiece);
  }

  @Override
  public void resolve(final StringWriter sw) {
    String combineStr = this.getTemplate().getFlywheel().getCombine();
    if (Stringop.isPopulated(combineStr) && "l".equals(getParams())) {
      resolveCombiner(sw, combineStr);
      return;
    }
    String parms = getParams();
    if (Stringop.isPopulated(parms)) {
      if (parms.equals(ReservedWords.TEMPLATE)) {
        sw.append(getTemplate().getRelativePath());
      } else {
        String tmplt = getTemplate().getFlywheel().get(getParams());
        if (tmplt != null && !getTemplate().getFlywheel().isReplacementsSuppressed()) {
          for (String[] s : getTemplate().getFlywheel().getReplacements()) {
            tmplt = tmplt.replace(s[0], s[1]);
          }
        }
        if (tmplt != null) {
          List<String> findLines = this.getTemplate().getFlywheel().getFindLines();
          if (findLines.isEmpty()) {
            sw.append(tmplt);
          } else {
            for (String fl : findLines) {
              if (Stringop.isPopulated(fl)) {
                if (tmplt.indexOf(fl) > -1) {
                  sw.append(tmplt);
                  continue;
                }
              }
            }
          }
        }
      }
    }
  }

  private void resolveCombiner(StringWriter sw, String combineStr) {
    int combineLines = Stringop.parseInt(combineStr);
    if (combineLines > 0) {
     CombineLineManager.INSTANCE.combine(sw, combineLines, getTemplate().getFlywheel().get(getParams())); 
    }
  }


  private enum CombineLineManager {
    INSTANCE;
    
    StringWriter assembledValue = new StringWriter();
    int currentCombine = 0;

    void combine(StringWriter sw, int combineLines, String value) {
      assembledValue.append(value);
      if (++currentCombine == combineLines) {
        sw.append(assembledValue.toString());
        assembledValue = new StringWriter();
        currentCombine = 0;
      }
    } 
  }
}
