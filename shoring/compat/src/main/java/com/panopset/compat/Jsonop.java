package com.panopset.compat;

import java.lang.reflect.Type;
import com.google.gson.Gson;

public class Jsonop {

  public String toJson(Object obj) {
    return new Gson().toJson(obj);
  }

  public Object fromJson(String json, Class<?> clazz) {
    return new Gson().fromJson(json, clazz);
  }

  /**
   *  https://stackoverflow.com/questions/5554217.
   */
  public Object listFromJson(String json, Type type) {
    return new Gson().fromJson(json, type);
  }
}
