package model;

import java.util.Scanner;

public class InventoryItem {
    private String name;
    private String category;
    private double price;
    private int quantity;

    public InventoryItem(String name, String category, double price, int quantity) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    // Add a static method to create an InventoryItem from user input
    public static InventoryItem createItemFromInput(Scanner scanner) {
        System.out.println("Enter the name:");
        String name = scanner.nextLine();
        System.out.println("Enter the category:");
        String category = scanner.nextLine();
        double price = 0;
        boolean validPrice = false;
        while (!validPrice) {
            System.out.println("Enter the price:");
            String priceInput = scanner.nextLine();
            try {
                price = Double.parseDouble(priceInput);
                validPrice = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input for price. Please enter a valid number.");
            }
        }
        int quantity = 0;
        boolean validQuantity = false;
        while (!validQuantity) {
            System.out.println("Enter the quantity:");
            String quantityInput = scanner.nextLine();
            try {
                quantity = Integer.parseInt(quantityInput);
                validQuantity = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input for quantity. Please enter a valid number.");
            }
        }
        return new InventoryItem(name, category, price, quantity);
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
