package com.example.myekouri.Domain;

public class CategoryDomain {
    String Category;
    String Image;
    String Name;

    public CategoryDomain() {
    }

    public CategoryDomain(String category, String image, String name) {
        Category = category;
        Image = image;
        Name = name;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
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
}