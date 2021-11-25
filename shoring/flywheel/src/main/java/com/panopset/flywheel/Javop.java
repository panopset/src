package com.panopset.flywheel;

import java.io.StringWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import com.panopset.compat.Logop;
import com.panopset.compat.MapProvider;
import com.panopset.compat.Nls;
import com.panopset.compat.Stringop;

public class Javop {
  private Javop() {}

  public static Map<String, String> copyMap(final Map<String, String> fromMap,
      final Map<String, String> toMap) {
    Map<String, String> rtn = toMap;
    if (rtn == null) {
      rtn = new HashMap<>();
    }
    if (fromMap != null) {
      for (Entry<String, String> entry : fromMap.entrySet()) {
        rtn.put(entry.getKey(), entry.getValue());
      }
    }
    return rtn;
  }

  public static Map<String, String> copyMapObjects(final Map<String, Object> fromMap,
      final Map<String, String> toMap) {
    Map<String, String> rtn = toMap;
    if (rtn == null) {
      rtn = new HashMap<>();
    }
    if (fromMap != null) {
      for (Entry<String, Object> entry : fromMap.entrySet()) {
        Object val = entry.getValue();
        if (val == null) {
          Logop.warn(String.format("%s %s", entry.getKey(), 
              " not found, will map it to blank."));
          rtn.put(entry.getKey(), "");
        } else {
          rtn.put(entry.getKey(), val.toString());
        }
      }
    }
    return rtn;
  }

  @SuppressWarnings("unchecked")
  public static String dump(final Object obj) {
    StringWriter sw = new StringWriter();
    if (obj == null) {
      return Nls.xlate("Can not dump null object") + ".";
    }
    if (obj instanceof Object[]) {
      for (Object s : (Object[]) obj) {
        sw.append(dump(s));
      }
    } else if (obj instanceof Map) {
      Map<Object, Object> map = (Map<Object, Object>) obj;
      for (Entry<Object, Object> entry : map.entrySet()) {
        sw.append(entry.getKey().toString());
        sw.append(Stringop.getEol());
        Object val = entry.getValue();
        if (val == null) {
          sw.append("null");
        } else {
          sw.append(val.toString());
        }
        sw.append(Stringop.getEol());
        sw.append(Stringop.getEol());
      }
    } else if (obj instanceof Collection) {
      for (Object e : (Collection<?>) obj) {
        sw.append(dump(e));
      }
    } else {
      sw.append(obj.toString());
      sw.append(Stringop.getEol());
    }
    return sw.toString();
  }

  public static String invokeStaticStringMethod(final String classMethodAndParms) {
    return invokeStaticStringMethod(classMethodAndParms, null);
  }

  public static String invokeStaticStringMethod(
      final String classMethodAndParms, final MapProvider mapProvider) {
    try {
      return new ReflectionInvoker.Builder()
          .classMethodAndParms(classMethodAndParms).mapProvider(mapProvider)
          .construct().exec();
    } catch (FlywheelException e) {
      return e.getMessage();
    }
  }
}
