package com.panopset.opspring;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public class HttpHelper {

    public String getClientIpAddress(HttpServletRequest request) {
        for (String header: HEADERS_TO_TRY) {
            String ip = request.getHeader(header);
            if (ip != null && ip.length() > 0 && !ip.toLowerCase(Locale.ROOT).contains("unknown")) {
                return ip;
            }
        }
        return request.getRemoteAddr();
    }

    private static String[] HEADERS_TO_TRY = new String[] {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"
    };
}
