package com.uc.appsinovatifguru.Model;

public class Menu {

    private String name;
    private int image;
    private Class nextClass;

    public Menu(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public Menu() {
    }

    public Class getNextClass() {
        return nextClass;
    }

    public void setNextClass(Class nextClass) {
        this.nextClass = nextClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
