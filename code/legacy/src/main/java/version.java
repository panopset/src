import com.panopset.compat.AppVersion;
import com.panopset.legacy.Warning;

public class version {
    public static void main(String... s) {
        Warning.main(s);
        System.out.println(AppVersion.INSTANCE.getFullVersion());
    }
}
