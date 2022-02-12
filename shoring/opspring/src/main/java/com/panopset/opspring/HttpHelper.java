package com.panopset.opspring;

import java.util.Locale;

import jakarta.servlet.http.HttpServletRequest;

public class HttpHelper {

    public String getClientIpAddress(HttpServletRequest request) {
        for (String header: HEADERS_TO_TRY) {
            String ip = request.getHeader(header);
            if (ip != null && ip.length() > 0 && !ip.toLowerCase(Locale.ROOT).contains("unknown")) {
                return String.format("%s : %s", header, ip);
            }
        }
    	return String.format("getRemoteAddr() = %s", request.getRemoteAddr());
    }

    private static String[] HEADERS_TO_TRY = new String[] {
            "X-Forward-For",
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP-X-FORWARDED-FOR",
            "HTTP_X_FORWARDED_FOR",
            "HTTP-X-FORWARDED",
            "HTTP_X_FORWARDED",
            "HTTP-X-CLUSTER-CLIENT-IP",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP-CLIENT-IP",
            "HTTP_CLIENT_IP",
            "HTTP-FORWARDED-FOR",
            "HTTP_FORWARDED_FOR",
            "HTTP-FORWARDED",
            "HTTP_FORWARDED",
            "HTTP-VIA",
            "HTTP_VIA",
            "REMOTE-ADDR",
            "REMOTE_ADDR"
    };
}
