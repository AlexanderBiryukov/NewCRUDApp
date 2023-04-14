package org.com.biryukov.CRUDproject.Views;

import java.util.Scanner;

public class ConsoleView {
    public void showMessage(String message) {
        System.out.println(message);
    }

    public String readLine() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
