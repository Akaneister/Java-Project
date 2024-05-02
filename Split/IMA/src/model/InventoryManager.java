package model;
import java.util.ArrayList;
import java.util.List;

public class InventoryManager {
    private List<InventoryItem> inventory;
    private List<InventoryCategory> categories;

    public InventoryManager() {
        this.inventory = new ArrayList<>();
        this.categories = new ArrayList<>();
    }

    // Method for adding inventory items
    public void addInventoryItem(InventoryItem newItem) {
        boolean itemExists = false;
        for (int i = inventory.size() - 1; i >= 0; i--) {
            InventoryItem existingItem = inventory.get(i);
            if (existingItem.getName().equalsIgnoreCase(newItem.getName()) && 
                existingItem.getCategory().equalsIgnoreCase(newItem.getCategory())) {
                // Item with same name and category already exists, update price and quantity
                existingItem.setPrice(newItem.getPrice());
                existingItem.setQuantity(existingItem.getQuantity() + newItem.getQuantity());
                itemExists = true;
                break;
            }
        }
        if (!itemExists) {
            // Item does not exist, add it to inventory
            inventory.add(newItem);
        }
    }

    // Method for viewing all inventory items
    public List<InventoryItem> viewInventory() {
        return new ArrayList<>(inventory); // Return a copy to prevent direct modification
    }

    // Method for updating inventory items with new values for all attributes
    public void updateInventoryItem(int index, String newName, String newCategory, double newPrice, int newQuantity) {
        if (index >= 0 && index < inventory.size()) {
            InventoryItem itemToUpdate = inventory.get(index);
            itemToUpdate.setName(newName);
            itemToUpdate.setCategory(newCategory);
            itemToUpdate.setPrice(newPrice);
            itemToUpdate.setQuantity(newQuantity);
        } else {
            System.out.println("Invalid index. Cannot update inventory item.");
        }
    }

    // Method for deleting inventory items
    public void deleteInventoryItem(int index) {
        if (index >= 0 && index < inventory.size()) {
            inventory.remove(index);
        } else {
            System.out.println("Invalid index. Cannot delete inventory item.");
        }
    }

    // Method for searching inventory items by name
    public List<InventoryItem> searchByName(String name) {
        List<InventoryItem> result = new ArrayList<>();
        for (InventoryItem item : inventory) {
            if (item.getName().equalsIgnoreCase(name)) {
                result.add(item);
            }
        }
        return result;
    }

    // Method for searching inventory items by category
    public List<InventoryItem> searchByCategory(String category) {
        List<InventoryItem> result = new ArrayList<>();
        for (InventoryItem item : inventory) {
            if (item.getCategory().equalsIgnoreCase(category)) {
                result.add(item);
            }
        }
        return result;
    }

    // Method for searching inventory items by price range
    public List<InventoryItem> searchByPriceRange(double minPrice, double maxPrice) {
        List<InventoryItem> result = new ArrayList<>();
        for (InventoryItem item : inventory) {
            if (item.getPrice() >= minPrice && item.getPrice() <= maxPrice) {
                result.add(item);
            }
        }
        return result;
    }

    // Method for searching inventory items by availability
    public List<InventoryItem> searchByAvailability(int minQuantity) {
        List<InventoryItem> result = new ArrayList<>();
        for (InventoryItem item : inventory) {
            if (item.getQuantity() >= minQuantity) {
                result.add(item);
            }
        }
        return result;
    }

    // Method to check if a category exists
    public boolean categoryExists(String category) {
        for (InventoryCategory cat : categories) {
            if (cat.getName().equalsIgnoreCase(category)) {
                return true;
            }
        }
        return false;
    }

    // Method to add a category
    public void addCategory(InventoryCategory category) {
        categories.add(category);
    }
}
