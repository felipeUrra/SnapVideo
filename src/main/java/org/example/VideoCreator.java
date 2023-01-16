package org.example;

import java.io.IOException;
import java.util.Scanner;

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

    public static void createVideo(Scanner scanner) {
        System.out.println("Video type:" + "\n" +
                "1. Simple video with out transitions" + "\n" +
                "2. Simple video with transitions" + "\n" +
                "3. Complex video with out transitions" + "\n" +
                "4. Complex video with transitions");

        switch (scanner.nextInt()) {
            case 1:
                try {
                    FileOperation.createFileForSimpleVideoWithOutTransitions(resourcePath, cmdPath);

                    String[] cmd = {"sh", cmdPath};
                    Runtime.getRuntime().exec(cmd);

                    System.out.println("Created video" + "\n");
                }
                catch (IOException ioe) {
                    System.out.println(ioe);
                }
                break;
            case 2:
                try {
                    FileOperation.createFileForSimpleVideoWithTransitions(resourcePath, cmdPath);

                    String[] cmd = {"sh", cmdPath};
                    Runtime.getRuntime().exec(cmd);

                    System.out.println("Created video" + "\n");
                }
                catch (IOException ioe) {
                    System.out.println(ioe);
                }
                break;
        }

    }
}
