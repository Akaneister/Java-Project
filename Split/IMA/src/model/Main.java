package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

public class Main {
    private static final String INVENTORY_FILE_NAME = "inventory.txt";

    public static void main(String[] args) {
        InventoryManager inventoryManager = new InventoryManager();
        loadInventoryFromFile(INVENTORY_FILE_NAME, inventoryManager);

        Scanner myObj = new Scanner(System.in);

        while (true) {
            System.out.println("__________________________");
            System.out.println("\n 1. Add an item \n 2. View inventory \n 3. Update an item \n 4. Delete an item \n 5. Search \n 6. Exit");
            System.out.println("__________________________");
            String rep = myObj.nextLine();

            switch (rep) {
                case "1":
                    do {
                        InventoryItem newItem = InventoryItem.createItemFromInput(myObj);
                        String category = newItem.getCategory();
                        if (!inventoryManager.categoryExists(category)) {
                            inventoryManager.addCategory(new InventoryCategory(category));
                        }
                        inventoryManager.addInventoryItem(newItem);
                        System.out.println("Item added successfully!");
                        System.out.println("\nDo you want to add another item? (Y/N)");
                        rep = myObj.nextLine();
                    } while (rep.equalsIgnoreCase("Y"));
                    break;
                case "2":
                    System.out.println("Viewing inventory...");
                    printInventoryByCategory(inventoryManager.viewInventory());
                    break;
                case "3":
                    // Handle update item case
                    System.out.println("Updating an item...");
                    System.out.println("\nEnter the name of the item you want to update:");
                    String itemNameToUpdate = myObj.nextLine();
                    List<InventoryItem> inventory = inventoryManager.viewInventory();
                    int itemIndexToUpdate = -1;
                    for (int i = 0; i < inventory.size(); i++) {
                        if (inventory.get(i).getName().equalsIgnoreCase(itemNameToUpdate)) {
                            itemIndexToUpdate = i;
                            break;
                        }
                    }
                    if (itemIndexToUpdate != -1) {
                        InventoryItem existingItem = inventory.get(itemIndexToUpdate);
                        System.out.println("Enter the new name:");
                        String newName = myObj.nextLine();
                        System.out.println("Enter the new category:");
                        String newCategory = myObj.nextLine();
                        System.out.println("Enter the new price:");
                        double newPrice = Double.parseDouble(myObj.nextLine());
                        System.out.println("Enter the new quantity:");
                        int newQuantity = Integer.parseInt(myObj.nextLine());
                        inventoryManager.updateInventoryItem(itemIndexToUpdate, newName, newCategory, newPrice, newQuantity);
                        System.out.println("Item updated successfully!");
                    } else {
                        System.out.println("Item not found. Cannot update inventory item.");
                    }
                    break;
                case "4":
                    // Handle delete item case
                    System.out.println("Deleting an item...");
                    System.out.println("\nEnter the name of the item you want to delete:");
                    String itemNameToDelete = myObj.nextLine();
                    List<InventoryItem> inventoryToDelete = inventoryManager.viewInventory();
                    int itemIndexToDelete = -1;
                    for (int i = 0; i < inventoryToDelete.size(); i++) {
                        if (inventoryToDelete.get(i).getName().equalsIgnoreCase(itemNameToDelete)) {
                            itemIndexToDelete = i;
                            break;
                        }
                    }
                    if (itemIndexToDelete != -1) {
                        inventoryManager.deleteInventoryItem(itemIndexToDelete);
                        System.out.println("Item deleted successfully!");
                    } else {
                        System.out.println("Item not found. Cannot delete inventory item.");
                    }
                    break;
                case "5":
                    // Code for searching
                    searchInventory(inventoryManager, myObj);
                    break;
                case "6":
                    saveInventoryToFile(INVENTORY_FILE_NAME, inventoryManager.viewInventory());
                    System.out.println("Exiting...");
                    return; // Exit the program
                default:
                    System.out.println("Invalid input. Please enter a valid option.");
                    break;
            }
        }
    }

    public static void printInventoryByCategory(List<InventoryItem> inventory) {
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
        } else {
            Map<String, List<InventoryItem>> itemsByCategory = new HashMap<>();
            for (InventoryItem item : inventory) {
                String category = item.getCategory();
                itemsByCategory.computeIfAbsent(category, k -> new ArrayList<>()).add(item);
            }

            System.out.println("Inventory Items by Category:");
            for (Map.Entry<String, List<InventoryItem>> entry : itemsByCategory.entrySet()) {
                System.out.println("-------------------------");
                System.out.println("Category: " + entry.getKey());
                System.out.println("-------------------------");
                for (InventoryItem item : entry.getValue()) {
                    System.out.println("Name: " + item.getName());
                    System.out.println("Price: $" + item.getPrice());
                    System.out.println("Quantity: " + item.getQuantity());
                    System.out.println("-------------------------");
                }
            }
        }
    }

    public static void searchInventory(InventoryManager inventoryManager, Scanner scanner) {
        System.out.println("Choose search criteria:");
        System.out.println("1. Search by name");
        System.out.println("2. Search by category");
        System.out.println("3. Search by price range");
        System.out.println("4. Search by availability");
        System.out.println("Enter your choice:");

        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                System.out.println("Enter the name to search:");
                String name = scanner.nextLine();
                List<InventoryItem> itemsByName = inventoryManager.searchByName(name);
                printSearchResults(itemsByName);
                break;
            case "2":
                System.out.println("Enter the category to search:");
                String category = scanner.nextLine();
                List<InventoryItem> itemsByCategory = inventoryManager.searchByCategory(category);
                printSearchResults(itemsByCategory);
                break;
            case "3":
                System.out.println("Enter the minimum price:");
                double minPrice = Double.parseDouble(scanner.nextLine());
                System.out.println("Enter the maximum price:");
                double maxPrice = Double.parseDouble(scanner.nextLine());
                List<InventoryItem> itemsByPriceRange = inventoryManager.searchByPriceRange(minPrice, maxPrice);
                printSearchResults(itemsByPriceRange);
                break;
            case "4":
                System.out.println("Enter the minimum quantity available:");
                int minQuantity = Integer.parseInt(scanner.nextLine());
                List<InventoryItem> itemsByAvailability = inventoryManager.searchByAvailability(minQuantity);
                printSearchResults(itemsByAvailability);
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    public static void printSearchResults(List<InventoryItem> items) {
        if (items.isEmpty()) {
            System.out.println("No items found matching the criteria.");
        } else {
            System.out.println("Search Results:");
            for (InventoryItem item : items) {
                System.out.println("Name: " + item.getName());
                System.out.println("Category: " + item.getCategory());
                System.out.println("Price: $" + item.getPrice());
                System.out.println("Quantity: " + item.getQuantity());
                System.out.println("-------------------------");
            }
        }
    }

    // Method to load inventory data from file
    private static void loadInventoryFromFile(String fileName, InventoryManager inventoryManager) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String name = parts[0];
                    String category = parts[1];
                    double price = Double.parseDouble(parts[2]);
                    int quantity = Integer.parseInt(parts[3]);
                    inventoryManager.addInventoryItem(new InventoryItem(name, category, price, quantity));
                }
            }
            System.out.println("Inventory data loaded from " + fileName);
        } catch (IOException e) {
            // If the file doesn't exist, create a new file
            System.out.println("The inventory file does not exist. Creating a new file...");
            try {
                // Create a new file
                FileWriter writer = new FileWriter(fileName);
                writer.close(); // Close the writer after creating the file
                System.out.println("New inventory file created: " + fileName);
            } catch (IOException ex) {
                System.out.println("An error occurred while creating the inventory file: " + ex.getMessage());
            }
        }
    }


    // Method to save inventory data to file
    private static void saveInventoryToFile(String fileName, List<InventoryItem> inventory) {
        try (FileWriter writer = new FileWriter(fileName)) {
            if (inventory.isEmpty()) {
                writer.write("Inventory is empty.\n");
            } else {
                for (InventoryItem item : inventory) {
                    writer.write(item.getName() + "," + item.getCategory() + "," + item.getPrice() + "," + item.getQuantity() + "\n");
                }
            }
            System.out.println("Inventory data has been saved to " + fileName);
        } catch (IOException e) {
            System.out.println("An error occurred while saving inventory data to file: " + e.getMessage());
        }
    }
}
