package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileOperation {

    public static void createFileForSimpleVideoWithOutTransitions(String resourcePath, String cmdPath) {
        FileWriter cmd = null;

        try {
            cmd = new FileWriter(cmdPath);

            BufferedWriter bufferedWriter = new BufferedWriter(cmd);

            for (int i = 0; i < 2; i++) {
                if (i == 0) {
                    bufferedWriter.write("cd " + resourcePath + "\n");
                } else {
                    bufferedWriter.write("ffmpeg -framerate 1/3 -pattern_type glob -i '*.jpg' -i audio.mp3 -r 25 -pix_fmt yuv420p video.mp4" + "\n");
                }
            }
            bufferedWriter.close();
            System.out.println("File created satisfactorily");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (cmd == null) {
                try {
                    cmd.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }

    public static void createFileForSimpleVideoWithTransitions(String resourcePath, String cmdPath) {
        FileWriter cmd = null;

        try {
            cmd = new FileWriter(cmdPath);

            BufferedWriter bufferedWriter = new BufferedWriter(cmd);

            for (int i = 0; i < 2; i++) {
                if (i == 0) {
                    bufferedWriter.write("cd " + resourcePath + "\n");
                } else {
                    bufferedWriter.write("ffmpeg \\\n" +
                            "-loop 1 -t 5 -i image_01.jpg \\\n" +
                            "-loop 1 -t 5 -i image_02.jpg \\\n" +
                            "-loop 1 -t 5 -i image_03.jpg \\\n" +
                            "-loop 1 -t 5 -i image_04.jpg \\\n" +
                            "-loop 1 -t 5 -i image_05.jpg \\\n" +
                            "-i audio.mp3 \\\n" +
                            "-filter_complex \\\n" +
                            "\"[1]format=yuva444p,fade=d=1:t=in:alpha=1,setpts=PTS-STARTPTS+4/TB[f0]; \\\n" +
                            " [2]format=yuva444p,fade=d=1:t=in:alpha=1,setpts=PTS-STARTPTS+8/TB[f1]; \\\n" +
                            " [3]format=yuva444p,fade=d=1:t=in:alpha=1,setpts=PTS-STARTPTS+12/TB[f2]; \\\n" +
                            " [4]format=yuva444p,fade=d=1:t=in:alpha=1,setpts=PTS-STARTPTS+16/TB[f3]; \\\n" +
                            " [0][f0]overlay[bg1];[bg1][f1]overlay[bg2];[bg2][f2]overlay[bg3]; \\\n" +
                            " [bg3][f3]overlay,format=yuv420p[v]\" -map \"[v]\" -map 5:a -shortest -movflags +faststart out.mp4");
                }
            }
            bufferedWriter.close();
            System.out.println("File created satisfactorily");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (cmd == null) {
                try {
                    cmd.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }
}
