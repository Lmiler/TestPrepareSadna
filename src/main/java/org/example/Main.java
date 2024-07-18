package org.example;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws URISyntaxException, IOException {
        ApiManager apiManager = new ApiManager();
        int choice = 0;
        while (choice != -1){
            System.out.println("enter 1 if you want to register. \n" +
                    "enter 2 if you want to get your tasks. \n" +
                    "enter 3 if you wan to add a task. \n" +
                    "enter 4 if you want to set a task as done. \n");
            Scanner scanner = new Scanner(System.in);
            choice = scanner.nextInt();
            System.out.println("what is your id");
            String id = new Scanner(System.in).nextLine();
            switch (choice){
                case 1 -> {
                    apiManager.register(id);
                }
                case 2 -> {
                    apiManager.getTask(id);
                }
                case 3 -> {
                    System.out.println("what is the task");
                    String task = new Scanner(System.in).nextLine();
                    apiManager.addTask(id, task);
                }
                case 4 -> {
                    System.out.println("what is the task");
                    String task = new Scanner(System.in).nextLine();
                    apiManager.setTaskDone(id, task);
                }
            }
        }
    }
}