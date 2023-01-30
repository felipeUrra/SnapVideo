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
            String[] imageAuxiliar = nameList[j].getName().split("\\.");

            extension = imageAuxiliar[imageAuxiliar.length - 1];

            if (extension.equals("jpg") || extension.equals("png")) {
                name = nameList[j].getName();

                imageAuxiliar = nameList[j].getName().split("_");

                if (imageAuxiliar.length == 3) {
                    duration = imageAuxiliar[2];
                    String[] durationAuxiliar = duration.split("\\.");
                    duration = durationAuxiliar[0];

                    images.add(new Image(name, duration, extension));
                } else if (imageAuxiliar.length == 2) {
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
                    bufferedWriter.write("ffmpeg -framerate 1/3 -pattern_type glob -i '*.jpg' -i audio.mp3 -r 25 -pix_fmt yuv420p out.mp4" + "\n");
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
                        ffmpegCmd += "-loop 1 -t 3" +
                                " -i " + xtractImagesData().get(j).getName() + " ";
                    }

                    ffmpegCmd += "-i audio.mp3 " +
                            "-filter_complex ";

                    ffmpegCmd += "\"[1]format=yuva444p,fade=d=1:t=in:alpha=1,setpts=PTS-STARTPTS+4/TB[f0];";

                    int numAux = 8;
                    for (int j = 2; j < xtractImagesData().size(); j++) {
                        int operAtom = j - 1;
                        ffmpegCmd += " [" + j + "]format=yuva444p,fade=d=1:t=in:alpha=1,setpts=PTS-STARTPTS+" + numAux + "/TB[f" + operAtom + "];";
                        numAux += 4;
                    }

                    ffmpegCmd += " [0][f0]overlay[bg1];";

                    for (int j = 1; j < xtractImagesData().size() - 2; j++) {
                        int operAtom = j + 1;
                        ffmpegCmd += "[bg" + j + "][f" + j + "]overlay[bg" + operAtom + "];";
                    }

                    int operAtom = xtractImagesData().size() - 2;
                    ffmpegCmd += " [bg" + operAtom + "][f" + operAtom + "]overlay,format=yuv420p[v]\" -map \"[v]\" -map " + xtractImagesData().size() + ":a -shortest -movflags +faststart outCE.mp4\n";

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

                    bufferedWriter.write("ffmpeg -f concat -i input.txt -i audio.mp3 -pix_fmt yuv420p -shortest outSD.mp4");
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

    public static void createVideoWithImagesWithVaryingSizes(String resourcePath, String cmdPath) {
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
                        ffmpegCmd += "-loop 1 -t 5" +
                                " -i " + xtractImagesData().get(j).getName() + " ";
                    }

                    ffmpegCmd += "-i audio.mp3 " +
                            "-filter_complex ";

                    ffmpegCmd += "\"[0:v]scale=1280:720:force_original_aspect_ratio=decrease,pad=1280:720:(ow-iw)/2:(oh-ih)/2,setsar=1,fade=t=out:st=4:d=1[v0]; ";

                    for (int j = 1; j < xtractImagesData().size(); j++) {
                        ffmpegCmd += "[" + j + ":v]scale=1280:720:force_original_aspect_ratio=decrease,pad=1280:720:(ow-iw)/2:(oh-ih)/2,setsar=1,fade=t=in:st=0:d=1,fade=t=out:st=4:d=1[v" + j + "]; ";
                    }

                    for (int j = 0; j < xtractImagesData().size(); j++) {
                        ffmpegCmd += "[" + j + "]";
                    }

                    ffmpegCmd += "concat=n=" + xtractImagesData().size() + ":v=1:a=0,overlay,format=yuv420p[v]\" -map \"[v]\" outIVS.mp4";

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
}
