package org.example;

import java.io.File;
import java.io.IOException;

public class VideoCreator {

    public static void createVideo() {
        try {
            String path = "/home/gemelos/projects/intelliJ/SnapVideo/SnapVideo.sh";
            String[] cmd = {"sh", path};
            Runtime.getRuntime().exec(cmd);
        }
        catch (IOException ioe) {
            System.out.println(ioe);
        }
    }

}
