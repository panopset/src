package com.panopset.compat;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlHelper {

  public static String getTextFromURL(final String urlStr) {
      URL url;
      try {
        url = new URL(urlStr);
        HttpGETclient client = new HttpGETclient(url);
        return client.getResponse();
      } catch (MalformedURLException mue) {
        Logop.warn(String.format("Bad URL: %s, %s",urlStr, mue.getMessage()));
        return "";
      } catch (Exception ex) {
    	Logop.warn(ex);
    	return "";
      }
  }
}
