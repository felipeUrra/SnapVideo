package org.example;

import java.util.Scanner;

public class App
{
    public static void main( String[] args ) {
        Scanner scanner = new Scanner(System.in);
        mostarMenu(scanner);
    }

    public static void mostarMenu(Scanner scanner) {
        boolean exit = false;

        System.out.println("SnapVideo");

        while (!exit) {
            System.out.println("1. Create video" + "\n" +
                    "2. To change code path" + "\n" +
                    "3. To change resource path" + "\n" +
                    "4. Salir");

            switch (scanner.nextInt()) {
                case 1:
                    System.out.println("Code path:");
                    scanner.skip("\n");
                    String path = scanner.nextLine();
                    VideoCreator.setCmdPath(path);

                    System.out.println("Resources path:");
                    VideoCreator.setResourcePath(scanner.nextLine());

                    VideoCreator.createVideo(scanner);
                    break;
                case 2:
                    System.out.println("New code path:");
                    scanner.skip("\n");
                    VideoCreator.setCmdPath(scanner.nextLine());
                    break;
                case 3:
                    System.out.println("New resource path:");
                    scanner.skip("\n");
                    VideoCreator.setResourcePath(scanner.nextLine());
                    break;
                case 4:
                    exit = true;
                    break;
            }
        }
    }
}
