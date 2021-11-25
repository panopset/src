import java.io.IOException;
import com.panopset.compat.Logop;
import com.panopset.desk.utilities.Flywheel;
import com.panopset.flywheel.ReflectionInvoker;

public class flywheel {

  public static void main(String... args) {
    if (args == null || args.length < 1) {
      ReflectionInvoker.defineTemplateAllowedReflection("dt", "com.panopset.flywheel.ReflectionInvoker.defineTemplateAllowedReflection");
      new Flywheel().go();
    } else {
      try {
        com.panopset.flywheel.Flywheel.main(args);
      } catch (IOException e) {
        Logop.error(e);
      }
    }
  }
}
