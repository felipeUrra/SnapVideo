package org.example;

import java.io.IOException;

public class VideoCreator {
    private static String cmdPath;
    private static String resourcePath;

    public static String getCmdPath() {
        return cmdPath;
    }

    public static void setCmdPath(String cP) {
        cmdPath = cP;
    }

    public static String getResourcePath() {
        return resourcePath;
    }

    public static void setResourcePath(String rP) {
        resourcePath = rP;
    }

    public static void createVideo() {
        try {
            String[] cmd = {"sh", cmdPath};
            Runtime.getRuntime().exec(cmd);

            System.out.println("Created video" + "\n");
        }
        catch (IOException ioe) {
            System.out.println(ioe);
        }
    }

}
