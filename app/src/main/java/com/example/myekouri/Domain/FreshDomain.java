package com.example.myekouri.Domain;

import java.io.Serializable;

public class FreshDomain implements Serializable {
    String Category,Brand,Image,Name,Description;
    double Price;

    public FreshDomain() {
    }

    public FreshDomain(String category, String brand, String image, String name, String description, double price) {
        Category = category;
        Brand = brand;
        Image = image;
        Name = name;
        Description = description;
        Price = price;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }
}
