package com.panopset.flywheel;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import com.panopset.compat.Logop;
import com.panopset.compat.Rezop;
import com.panopset.compat.Stringop;

/**
 * <h1>l - List</h1>
 *
 * <p>
 * Example 1
 * </p>
 *
 * <pre>
 * ${&#064;l someListFile.txt}
 * </pre>
 *
 * <p>
 * Example 2
 * </p>
 *
 * <pre>
 * ${&#064;p listFileName}someListFile.txt${&#064;q}
 * ${&#064;l @listFileName}
 * </pre>
 * <p>
 * Read the given file, and for each line execute the template from this list
 * command up until its matching q command.
 * </p>
 * <p>
 * You may also specify a jar resource, for example
 * </p>
 *
 * <pre>
 *   /com/company/myList.txt
 * </pre>
 *
 * <p>
 * If no set of tokens is defined, each line will be stored in variable
 * <b>1</b>. If there are tokens defined, the line will be split by your tokens,
 * and stored in variables named after integers, in order. Tokens may be defined
 * by the Flywheel.Builder.tokens method, or by specifying them in the reserved
 * variable name <b>${com.panopset.flywheel.tokens}</b>
 * </p>
 * <p>
 * Another way to split the lines is by columns. If no tokens are defined, you
 * may specify comma delimited column separators in the reserved variable name
 * <b>${com.panopset.flywheel.splits}</b>. For example <b>5,10</b>.
 * </p>
 * <p>
 *
 * List commands can be nested, as each list command has its own cascading
 * variable definitions. Just remember that any top level list has to define
 * numbered variables as different variable names in order for lower level lists
 * to use them.
 * </p>
 */
public class CommandList extends MatchableCommand implements
    UserMatchableCommand {

  /**
   * Short HTML text for publishing command format in an HTML document.
   *
   * @return <b>${&#064;l someListFile.txt}</b>.
   */
  public static String getShortHtmlText() {
    return "${&#064;l someListFile.txt}";
  }

  public CommandList(final TemplateLine templateLine, final String innerPiece,
      final Template template) {
    super(templateLine, innerPiece, template);
  }

  @Override
  public final void resolve(final StringWriter sw) throws FlywheelException {
    SourceFile sourceFile = new SourceFile(getTemplate().getFlywheel(),
        getParams());
    List<String> lines = new ArrayList<>();
    if (sourceFile.isValid()) {
      lines = sourceFile.getSourceLines();
    } else {
      InputStream is = this.getClass().getResourceAsStream(getParams());
      if (is != null) {
        lines = Rezop.textStreamToList(is);
      }
    }
    if (lines == null || lines.isEmpty()) {
      return;
    }
    String tokens = getTemplate().getFlywheel().get(ReservedWords.TOKENS);
    String splits = getTemplate().getFlywheel().get(ReservedWords.SPLITS);
    getTemplate()
          .getFlywheel()
          .getMapStack()
          .push(
              new NamedMap<>(String.format("key_%d_%s", Stringop.getNextJvmUniqueID(), getParams())));
    for (String s : lines) {
      if (!Stringop.isPopulated(s)) {
        continue;
      }
      Logop.debug(String.format("CommandList line:%s", s));
      new TokenVariableFactory().addTokensToMap(getTemplate().getFlywheel().getTopMap(), s, tokens);
      if (Stringop.isPopulated(splits)) {
        StringTokenizer st = new StringTokenizer(splits, ",");
        int count = 1;
        int startAt = 0;
        int split = 0;
        while (st.hasMoreElements()) {
          split = Integer.parseInt(st.nextToken()) - 1;
          if (s.length() >= split) {
            getTemplate().getFlywheel().put("" + count++,
                processValue(s.substring(startAt, split)));
            startAt = split;
          }
        }
        if (s.length() > split) {
          getTemplate().getFlywheel().put(String.format("%d", count),
              processValue(s.substring(split)));
        }
      } else {
        getTemplate().getFlywheel().put("1", processValue(s));
      }
      resolveMatchedCommands(sw);
    }
    getTemplate().getFlywheel().getMapStack().pop();
  }

  /**
   * Process a list value, resolving any template commands within the line.
   *
   * @param str
   *          String to be processed.
   * @return processed value.
   */
  private String processValue(final String str) {
    StringWriter sw = new StringWriter();
    new Template(getTemplate().getFlywheel(), new TemplateArray(
        new String[] { str }), LineFeedRules.FLATTEN).exec(sw);
    return sw.toString();
  }
}
