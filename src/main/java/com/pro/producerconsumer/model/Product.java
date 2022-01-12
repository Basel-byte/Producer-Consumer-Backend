package com.pro.producerconsumer.model;

import java.awt.*;

public class Product implements Cloneable{
    private String color;
    private int interval;

    public Product(String color, int interval) {
        this.color = color;
        this.interval = interval;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Product clonedProduct = null;
        try {
            clonedProduct = (Product) super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return clonedProduct;
    }
}
