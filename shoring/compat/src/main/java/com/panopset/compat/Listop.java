package com.panopset.compat;

public class Listop {
    public String filter(String input, String filter) {


        if (input == null) {
            Logop.warn("No input.");
            return "";
        }

        if (filter == null) {
            Logop.warn("No input.");
            return "";
        }

        StringBuffer sb = new StringBuffer();
        for (String  inp :  Stringop.stringToList(input)) {
            for (String fs : Stringop.stringToList(filter)) {
                if (inp.equals(fs)) {
                    continue;
                }
                sb.append(inp);
                sb.append(Stringop.getEol());
            }
        }
        return sb.toString();
    }
}
