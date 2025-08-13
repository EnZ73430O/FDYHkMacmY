// 代码生成时间: 2025-08-14 06:46:14
package com.yourcompany.cart;

import io.quarkus.runtime.annotations.RegisterForReflection;
import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RegisterForReflection
public class ShoppingCartService {

    private Map<String, ShoppingCartItem> itemsMap = new HashMap<>();
    private List<ShoppingCartItem> items = new ArrayList<>();

    /**
     * Adds an item to the shopping cart.
     *
     * @param item The item to be added.
     */
    public void addItem(ShoppingCartItem item) {
        if (item == null || item.getProductId() == null || item.getQuantity() <= 0) {
            throw new IllegalArgumentException("Invalid item parameters.");
        }

        ShoppingCartItem existingItem = itemsMap.get(item.getProductId());
        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
        } else {
            itemsMap.put(item.getProductId(), item);
            items.add(item);
        }
    }

    /**
     * Removes an item from the shopping cart.
     *
     * @param productId The product ID of the item to be removed.
     */
    public void removeItem(String productId) {
        ShoppingCartItem item = itemsMap.remove(productId);
        if (item == null) {
            throw new NotFoundException("Item with product ID: " + productId + " not found.");
        }
        items.remove(item);
    }

    /**
     * Gets the total price of all items in the shopping cart.
     *
     * @return The total price.
     */
    public double getTotalPrice() {
        double total = 0;
        for (ShoppingCartItem item : items) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }

    /**
     * Represents a shopping cart item.
     */
    public static class ShoppingCartItem {
        private String productId;
        private String name;
        private double price;
        private int quantity;

        public ShoppingCartItem(String productId, String name, double price, int quantity) {
            this.productId = productId;
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }

        public String getProductId() {
            return productId;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}
