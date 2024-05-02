package model;

import java.util.ArrayList;
import java.util.List;

public class InventoryCategory {
    private String name;
    private List<InventoryItem> items;

    public InventoryCategory(String name) {
        this.name = name;
        this.items = new ArrayList<>();
    }

    // Add an item to the category
    public void addItem(InventoryItem item) {
        items.add(item);
    }

    // Get all items in the category
    public List<InventoryItem> getItems() {
        return items;
    }

    // Get the name of the category
    public String getName() {
        return name;
    }

    // Set the name of the category
    public void setName(String name) {
        this.name = name;
    }
}
