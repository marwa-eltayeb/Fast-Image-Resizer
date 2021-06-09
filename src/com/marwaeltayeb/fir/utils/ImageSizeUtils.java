package com.marwaeltayeb.fir.utils;

public class ImageSizeUtils {

    public static int getSize(String key){
        switch(key) {
            case "ldpi 36x36":
                return 36;
            case "mdpi 48x48":
                return 48;
            case "tvdpi 64x64":
                return 64;
            case "hdpi 72x72":
                return 72;
            case "xhdpi 96x96":
                return 96;
            case "xxhdpi 144x144":
                return 144;
            case "xxxhdpi 192x192":
                return 192;
            default:
                return 0;
        }
    }
}
