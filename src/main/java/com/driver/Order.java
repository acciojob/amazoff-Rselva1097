package com.driver;

public class Order {

    private String id;
    private int deliveryTime;

    public Order(String id, String deliveryTime) {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        String[] split=deliveryTime.split(":",0);
        int h=Integer.parseInt(split[0]);
        int m=Integer.parseInt(split[1]);

        int timeInMin=h * 60 + m;
        this.id=id;
        this.deliveryTime=timeInMin;
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}
}
