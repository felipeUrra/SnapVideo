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
                "1. Simple video without transitions" + "\n" +
                "2. Simple video with croosfade effect" + "\n" +
                "3. Complex video without transitions" + "\n" +
                "4. Complex video with images with varying sizes");

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
                    FileOperation.createFileForSimpleVideoWithCrossfadeEffect(resourcePath, cmdPath);

                    String[] cmd = {"sh", cmdPath};
                    Runtime.getRuntime().exec(cmd);

                    System.out.println("Created video" + "\n");
                }
                catch (IOException ioe) {
                    System.out.println(ioe);
                }
                break;
            case 3:
                try {
                    FileOperation.createFileForVideoWithSpecificDurationsWithOutTransitions(resourcePath, cmdPath);

                    String[] cmd = {"sh", cmdPath};
                    Runtime.getRuntime().exec(cmd);

                    System.out.println("Created video" + "\n");
                } catch (IOException ioe) {
                    System.out.println(ioe);
                }
                break;
            case 4:
                try {
                    FileOperation.createVideoWithImagesWithVaryingSizes(resourcePath, cmdPath);

                    String[] cmd = {"sh", cmdPath};
                    Runtime.getRuntime().exec(cmd);

                    System.out.println("Created video" + "\n");
                } catch (IOException ioe) {
                    System.out.println(ioe);
                }
                break;
        }

    }
}
