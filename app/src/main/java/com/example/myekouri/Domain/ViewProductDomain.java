package com.example.myekouri.Domain;

import java.io.Serializable;

public class ViewProductDomain implements Serializable {
    String Name, Brand, Description, Image, Category;
    double Price;

    public ViewProductDomain() {
    }

    public ViewProductDomain(String name, String brand, String description, String image, String category, double price) {
        Name = name;
        Brand = brand;
        Description = description;
        Image = image;
        Category = category;
        Price = price;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }
}