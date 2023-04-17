package org.example;

import org.com.biryukov.CRUDproject.Controller.DeveloperController;
import org.com.biryukov.CRUDproject.Views.ConsoleView;

public class Main {
    public static void main(String[] args) {
        ConsoleView console = new ConsoleView();
        DeveloperController developerController = new DeveloperController();
        System.out.println("\nДобро пожаловать!");

        while (true) {
            System.out.println("-----------------------------------------------------------------");
            System.out.println(" Чтобы получить список всех разработчиков введите команду \"get\"" +
                    "\n Для выхода введите команду \"exit\"");
            System.out.println("-----------------------------------------------------------------");

            String message = console.readLine();
            if (message.trim().equals("exit")) {
                System.out.println("\nДо свидания!");
                break;

            } else if (message.trim().equals("get")) {

                if ((developerController.getListAllDevelopers() == null) ||
                        developerController.getListAllDevelopers().isEmpty()) {
                    System.out.println("\nСписок разработчиков пуст");
                    System.out.println(" * Для добавления нового разработчика в список введите команду \"add\"" +
                            "\n * Для выхода введите команду \"exit\"");

                    String message2 = console.readLine();
                    if (message2.trim().equals("add")) {
                        developerController.createDeveloper();  // создаем нового разработчика и добавляем в наш файл

                    } else if (message2.trim().equals("exit")) {
                        System.out.println("До свидания!");
                        break;
                    }


                } else {
                    developerController.index();  // выводится список разработчиков
                    System.out.println(" * Для добавления нового разработчика в список введите команду \"add\"");
                    System.out.println(" * Для редактирования введите команду \"update\"");
                    System.out.println(" * Для удаления введите команду \"delete\"");
                    System.out.println(" * Для выхода введите команду \"exit\"");
                    String message3 = console.readLine();

                        switch (message3.trim()) {
                            case "add":
                                developerController.createDeveloper();
                                break;
                            case "update":
                                developerController.updateDeveloper();
                                break;
                            case "delete":
                                developerController.delete();
                            case "exit":
                                break;
                            default:
                                System.out.println("Некорректный ввод");
                        }
                }
            } else {
                System.out.println("Некорректный ввод");
            }


        }

    }
}