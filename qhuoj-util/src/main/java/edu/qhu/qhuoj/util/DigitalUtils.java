package edu.qhu.qhuoj.util;

public class DigitalUtils {
    public static String getRandomString(int length, String mode) {
        StringBuffer buffer = new StringBuffer();
        String characters = "";

        switch(mode) {
            case "ALPHA":
                characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
                break;
            case "ALPHANUMERIC":
                characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
                break;
            case "NUMERIC":
                characters = "1234567890";
                break;
        }

        int charactersLength = characters.length();
        for ( int i = 0; i < length; ++ i ) {
            double index = Math.random() * charactersLength;
            buffer.append(characters.charAt((int) index));
        }
        return buffer.toString();
    }
}
