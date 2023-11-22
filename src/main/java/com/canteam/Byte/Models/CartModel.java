package com.canteam.Byte.Models;

import java.util.HashMap;

public class CartModel {
    private CartModel() {}
    private static String itemName;
    private static double itemPrice;
    private static int itemQuantity;
    private static String itemStore;
    private static double itemTotalPrice;
    private static String itemInstructions;

    // order
    private static String modeOfPayment;
    private static float orderSubtotal;
    private static float orderTotalPrice;

    private static HashMap<String, HashMap<String, String>> cart = new HashMap<String, HashMap<String, String>>();
    private static HashMap<String, String> item;

    private static void addToCart(String name, double price, int quantity, String store, String instructions) {
        if (cart.containsKey(name)) {
            HashMap<String, String> nestedMap = cart.get(name);
            int value = Integer.parseInt(nestedMap.get("Quantity"));
            nestedMap.put("Quantity", String.valueOf((value+quantity)));
            nestedMap.put("Total Price", Double.toString((value+quantity)*price));
            orderSubtotal += (float) (price * quantity);
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
            orderSubtotal += (float) (itemTotalPrice * quantity);
        }
        orderTotalPrice = orderSubtotal + 20;
    }

    private static void placeOrder() {}

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
