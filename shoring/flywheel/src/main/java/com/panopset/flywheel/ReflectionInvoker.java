package com.panopset.flywheel;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import com.panopset.compat.Logop;
import com.panopset.compat.MapProvider;

public class ReflectionInvoker {

  private MapProvider pmapProvider;
  private Object pobject;
  private Class<?> pclazz;
  private String pmethod;
  private String pparms;

  public String exec() {
    StringTokenizer st = new StringTokenizer(pparms, ",");
    Object[] args = new String[st.countTokens()];
    Class<?>[] parmTypes = new Class<?>[st.countTokens()];
    int incr = 0;
    while (st.hasMoreTokens()) {
      String key = st.nextToken();
      String val = key;
      if (pmapProvider != null) {
        String str = pmapProvider.get(key);
        if (str != null) {
          val = str;
        }
      }
      args[incr] = val;
      parmTypes[incr] = String.class;
      incr++;
    }
    if (pclazz == null) {
      return "";
    }
    Object rtn;
    try {
      rtn = pclazz.getMethod(pmethod, parmTypes).invoke(pobject, args);
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
        | NoSuchMethodException | SecurityException ex) {
      Logop.error(ex.getMessage());
      return "";
    }
    if (rtn == null) {
      return "";
    }
    return rtn.toString();
  }

  public static final List<ClassLoader> CLASSLOADERS = new ArrayList<>();

  public static final class Builder {

    private Object bobject;
    private String bclassName;
    private Class<?> bclazz;
    private String bmethod;
    private String bparms;
    private MapProvider bmapProvider;

    public ReflectionInvoker construct() throws FlywheelException {
      ReflectionInvoker rtn = new ReflectionInvoker();
      rtn.pobject = bobject;
      if (bobject == null) {
        if (bclazz == null) {
          rtn.pclazz = loadClass(bclassName);
        } else {
          rtn.pclazz = bclazz;
        }
      } else {
        rtn.pclazz = bobject.getClass();
      }
      rtn.pmethod = bmethod;
      rtn.pparms = bparms;
      rtn.pmapProvider = bmapProvider;
      return rtn;
    }

    private Class<?> loadClass(String name) {
      Class<?> rtn = null;
      try {
        rtn = Class.forName(name);
      } catch (ClassNotFoundException ex) {
        Logop.debug(String.format("Failed to load %s, trying another classloader. %s.", name,
            ex.getMessage()));
      }
      if (rtn != null) {
        return rtn;
      }
      rtn = loadClass(Thread.currentThread().getContextClassLoader(), bclassName);
      if (rtn != null) {
        return rtn;
      }
      for (ClassLoader cl : CLASSLOADERS) {
        rtn = loadClass(cl, bclassName);
        if (rtn != null) {
          break;
        }
      }
      return rtn;
    }


    private Class<?> loadClass(ClassLoader classLoader, String name) {
      Class<?> rtn = null;
      try {
        rtn = classLoader.loadClass(name);
      } catch (ClassNotFoundException ex) {
        Logop.debug(
            String.format("Failed to load %s, trying another classloader.  %s.", name, ex.getMessage()));
      }
      return rtn;
    }

    /**
     * Optional, if not specified, either className or classMethodAndParms must be specified.
     *
     * @param object Object method is to be invoked on.
     * @return Builder.
     */
    public Builder object(final Object object) {
      this.bobject = object;
      return this;
    }

    /**
     * Optional, if not specified, either object or classMethodAndParms must be specified.
     *
     * @param className className
     * @return Builder.
     */
    public Builder className(final String className) {
      this.bclassName = className;
      return this;
    }

    /**
     * If not specified, static methods only may be invoked.
     */
    public Builder clazz(final Class<?> clazz) {
      this.bclazz = clazz;
      return this;
    }

    public Builder method(final String method) {
      this.bmethod = method;
      return this;
    }

    public Builder parms(final String parms) {
      this.bparms = parms;
      return this;
    }

    /**
     * Optional mapProvider. If specified, the mapProvider will be checked for parameter values. If
     * the parameter does not match any of the mapProvider's keys, then the parameter is simply used
     * by itself.
     */
    public Builder mapProvider(final MapProvider mapProvider) {
      this.bmapProvider = mapProvider;
      return this;
    }

    public Builder methodAndParms(final String methodAndParms) {
      int paramsStart = methodAndParms.indexOf("(");
      if (paramsStart < 2) {
        Logop.error(String.format("Format should be function(parms), found: %s", methodAndParms));
      }
      bmethod = methodAndParms.substring(0, paramsStart);
      bparms = methodAndParms.substring(paramsStart + 1, methodAndParms.length() - 1);
      return this;
    }

    /**
     * Optional, if not specified either object or className must be specified.
     */
    public Builder classMethodAndParms(final String classKeyAndParams) throws FlywheelException {
      int i = classKeyAndParams.indexOf("(");
      try {
        String key = classKeyAndParams.substring(0, i);
        String params = classKeyAndParams.substring(i);
        FlywheelFunction fwf = getMap().get(key);
        if (fwf == null) {
          throw new FlywheelException(String.format("Function not defined: %s", classKeyAndParams));
        }
        String methodAndParms = String.format("%s%s", fwf.getMethodName(), params);
        return className(String.format("%s.%s", fwf.getPackageName(), fwf.getClassName()))
            .methodAndParms(methodAndParms);
      } catch (StringIndexOutOfBoundsException ex) {
        throw new FlywheelException(String.format("Function syntax error: %s", classKeyAndParams));
      }
    }
  }

  public static void defineTemplateAllowedReflection(String key, String pkgClassFunction) {
    int i = pkgClassFunction.lastIndexOf(".");
    String fullClassName = pkgClassFunction.substring(0, i);
    String methodName = pkgClassFunction.substring(i + 1);
    i = fullClassName.lastIndexOf(".");
    String packageName = fullClassName.substring(0, i);
    String className = fullClassName.substring(i + 1);
    FlywheelFunction ff = new FlywheelFunction(key, packageName, className, methodName, "");
    defineTemplateAllowedReflection(key, ff);
  }

  public static void defineTemplateAllowedReflection(String key, String packageName,
      String className, String functionName, String example) {
    FlywheelFunction ff = new FlywheelFunction(key, packageName, className, functionName, example);
    defineTemplateAllowedReflection(key, ff);
  }

  public static void defineTemplateAllowedReflection(String key, FlywheelFunction ff) {
    getMap().put(key, ff);
  }

  public static final Collection<FlywheelFunction> getAll() {
    Flywheel.defineAllowedScriptCalls();
    return getMap().values();
  }

  public static final Map<String, FlywheelFunction> getMap() {
    if (MAP == null) {
      MAP = Collections.synchronizedSortedMap(new TreeMap<String, FlywheelFunction>());
    }
    return MAP;
  }

  private static Map<String, FlywheelFunction> MAP;
}
