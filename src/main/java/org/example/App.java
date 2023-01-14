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
                    "4. Salir" + "\n" +
                    ":");

            switch (scanner.nextInt()) {
                case 1:
                    System.out.println("Code path:");
                    String path = scanner.next();
                    VideoCreator.setCmdPath(path);

                    System.out.println("Resources path:");
                    VideoCreator.setResourcePath(scanner.next());

                    VideoCreator.createVideo();
                    break;
                case 2:
                    System.out.println("Code path:");
                    VideoCreator.setCmdPath(scanner.nextLine());
                    break;
            }
        }
    }
}
