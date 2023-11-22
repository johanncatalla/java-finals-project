package com.canteam.Byte.Models;

import java.util.HashMap;

public class CartModel {
    private CartModel() {}
    private static String itemName = "";
    private static double itemPrice = 0.0;
    private static int itemQuantity = 0;
    private static String itemStore = "";
    private static double itemTotalPrice = 0.0;
    private static String itemInstructions = "";

    // order
    private static String modeOfPayment = "";
    private static double orderSubtotal = 0.0;
    private static double orderTotalPrice = 0.0;

    private static HashMap<String, HashMap<String, String>> cart = new HashMap<String, HashMap<String, String>>();
    private static HashMap<String, String> item;

    private static void addToCart(String name, double price, int quantity, String store, String instructions) {
        if (cart.containsKey(name)) {
            HashMap<String, String> nestedMap = cart.get(name);
            int value = Integer.parseInt(nestedMap.get("Quantity"));
            nestedMap.put("Quantity", String.valueOf((value+quantity)));
            nestedMap.put("Total Price", Double.toString((value+quantity)*price));
            orderSubtotal += (double) (price * quantity);
        } else {
            item = new HashMap<String, String>();
            itemName = name;
            itemPrice = price;
            itemQuantity = quantity;
            itemStore = store;
            itemTotalPrice = price * quantity;
            itemInstructions = instructions;

            item.put("Name", name);
            item.put("Price", Double.toString(price));
            item.put("Quantity", Integer.toString(quantity));
            item.put("Store", store);
            item.put("Total Price", Double.toString(itemTotalPrice));
            item.put("Instructions", instructions);

            cart.put(itemName, item);
            orderSubtotal += (double) (itemTotalPrice * quantity);
        }
        orderTotalPrice = orderSubtotal + 20;
    }

    private static void placeOrder() {
        itemName = "";
        itemPrice = 0;
        itemQuantity = 0;
        itemStore = "";
        itemTotalPrice = 0;
        itemInstructions = "";
        orderSubtotal = 0;
        orderTotalPrice = 0;
        cart.clear();
        item.clear();
    }

    // setters
    public static void setItemName(String inputItemName) { itemName = inputItemName; }
    public static void setItemPrice(double inputItemPrice) { itemPrice = inputItemPrice; }
    public static void setItemQuantity(int inputItemQuantity) { itemQuantity = inputItemQuantity; }
    public static void setItemStore(String inputItemStore) { itemStore = inputItemStore; }
    public static void setItemTotalPrice(double inputItemTotalPrice) { itemTotalPrice = inputItemTotalPrice; }
    public static void setItemInstructions(String inputItemInstructions) { itemInstructions = inputItemInstructions; }
    public static void setModeOfPayment(String inputModeOfPayment) { modeOfPayment = inputModeOfPayment; }

    //getters
    public static String getItemName() { return itemName; }
    public static double getItemPrice() { return itemPrice; }
    public static int getItemQuantity() { return itemQuantity; }
    public static String getItemStore() { return itemStore; }
    public static double getItemTotalPrice() { return itemTotalPrice; }
    public static String getItemInstructions() { return itemInstructions; }
    public static String getModeOfPayment() { return modeOfPayment; }
    public static HashMap<String, String> getItem() { return item; }
    private static HashMap<String, HashMap<String, String>> getCart() { return cart; }
    private static double getSubtotal() { return orderSubtotal; }
    private static double getTotalPrice() { return orderTotalPrice; }


    public static void main(String[] args) {
        CartModel.addToCart("test",100, 1,"test", "test");
        CartModel.addToCart("test",100, 2,"test", "test");
        CartModel.addToCart("hehe",100, 1,"test", "test");
        System.out.println(CartModel.getCart());
        System.out.println("Subtotal: "+CartModel.getSubtotal());
        System.out.println("Total: "+CartModel.getTotalPrice());
    }
}
