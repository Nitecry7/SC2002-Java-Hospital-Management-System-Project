package hmsystem.models;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Medicine {
    private String name;
    private int alertLine, stock, request = 0;

    public Medicine() {
        build();
    }

    private void build() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter medicine name: ");
        this.name = sc.nextLine();

        this.alertLine = inputInt(sc, "Enter alert line: ");
        this.stock = inputInt(sc, "Enter stock: ");
        
        sc.close();
    }

    private int inputInt(Scanner sc, String message) {
        int value = 0;
        while (true) {
            System.out.print(message);
            try {
                value = sc.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                sc.next();
            }
        }
        return value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAlertLine() {
        return alertLine;
    }

    public void setAlertLine(int alertLine) {
        this.alertLine = alertLine;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getRequest() {
        return request;
    }

    public void setRequest(int request) {
        this.request = request;
    }

    public boolean reduceStock(int amount){
        if(stock >= amount){
            System.out.printf("Successfully reduced %d amount of %s\n", amount, name);
            return true;
        }
        System.out.printf("Error! %s not enough!\n", name);
        return false;
    }
}
