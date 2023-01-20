package org.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

public class FileOperation {
    public static LinkedList<Image> xtractImagesData() {
        File folder = new File("/home/gemelos/Videos/videoJava");
        File[] nameList = folder.listFiles();

        Arrays.sort(nameList, new Comparator<File>() {
            @Override
            public int compare(File f1, File f2) {
                if (!f1.equals(f2) && f1.equals("nombredelfichero")){
                    return -1;
                }else if (!f1.equals(f2) && f2.equals("nombredelfichero")){
                    return 1;
                }else{
                    return f1.compareTo(f2);
                }
            }
        });

        LinkedList<Image> images = new LinkedList<>();
        String name;
        String duration;
        String extension;

        for (int j = 0; j < nameList.length; j++) {
            int i = nameList[j].getName().lastIndexOf('.');
            extension = nameList[j].getName().substring(i+1);

            if (extension.equals("jpg") || extension.equals("png")) {
                name = nameList[j].getName();

                if ("_".equals(nameList[j].getName().substring(7, 8))) {
                    duration = nameList[j].getName().substring(17, 18);
                    images.add(new Image(name, duration, extension));

                } else if ("_".equals(nameList[j].getName().substring(8, 9))) {
                    duration = nameList[j].getName().substring(18, 19);
                    images.add(new Image(name, duration, extension));

                } else {
                    images.add(new Image(name, "3", extension));
                }
            }
        }
        return images;
    }

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

    public static void createFileForSimpleVideoWithCrossfadeEffect(String resourcePath, String cmdPath) {
        FileWriter cmd = null;

        try {
            cmd = new FileWriter(cmdPath);

            BufferedWriter bufferedWriter = new BufferedWriter(cmd);

            for (int i = 0; i < 2; i++) {
                if (i == 0) {
                    bufferedWriter.write("cd " + resourcePath + "\n");
                } else {
                    String ffmpegCmd = "ffmpeg ";

                    for (int j = 0; j < xtractImagesData().size(); j++) {
                        ffmpegCmd += "-loop 1 -t " + xtractImagesData().get(j).getDuration() +
                                " -i " + xtractImagesData().get(j).getName() + " ";
                    }

                    ffmpegCmd += "-i audio.mp3 " +
                            "-filter_complex ";

                    ffmpegCmd += "\"[1]format=yuva444p,fade=d=1:t=in:alpha=1,setpts=PTS-STARTPTS+4/TB[f0];" +
                            " [2]format=yuva444p,fade=d=1:t=in:alpha=1,setpts=PTS-STARTPTS+8/TB[f1];" +
                            " [3]format=yuva444p,fade=d=1:t=in:alpha=1,setpts=PTS-STARTPTS+12/TB[f2];" +
                            " [4]format=yuva444p,fade=d=1:t=in:alpha=1,setpts=PTS-STARTPTS+16/TB[f3];" +
                            " [0][f0]overlay[bg1];[bg1][f1]overlay[bg2];[bg2][f2]overlay[bg3];" +
                            " [bg3][f3]overlay,format=yuv420p[v]\" -map \"[v]\" -map 5:a -shortest -movflags +faststart out.mp4\n";

                    bufferedWriter.write(ffmpegCmd);
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

    public static void createFileForVideoWithSpecificDurationsWithOutTransitions(String resourcePath, String cmdPath) {
        FileWriter cmd = null;

        try {
            cmd = new FileWriter(cmdPath);

            BufferedWriter bufferedWriter = new BufferedWriter(cmd);

            for (int i = 0; i < 2; i++) {
                if (i == 0) {
                    bufferedWriter.write("cd " + resourcePath + "\n");
                } else {
                    FileWriter input = null;

                    input = new FileWriter(resourcePath + "/input.txt");

                    BufferedWriter bufferedWriterInput = new BufferedWriter(input);

                    for (int j = 0; j < xtractImagesData().size(); j++) {
                        bufferedWriterInput.write("file " + xtractImagesData().get(j).getName() + "\n");
                        bufferedWriterInput.write("outpoint " + xtractImagesData().get(j).getDuration() + "\n");

                        if (j == xtractImagesData().size() - 1) {
                            bufferedWriterInput.write("file " + xtractImagesData().get(j).getName());
                        }
                    }
                    bufferedWriterInput.close();

                    bufferedWriter.write("ffmpeg -f concat -i input.txt -i audio.mp3 -pix_fmt yuv420p -shortest videoSpec.mp4");
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
