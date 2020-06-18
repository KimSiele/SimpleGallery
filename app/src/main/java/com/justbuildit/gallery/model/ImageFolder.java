package com.justbuildit.gallery.model;

public class ImageFolder {

    private String pathName;
    private String folderName;
    private int number_of_images = 0;
    private String img_first;

    public ImageFolder() {
    }

    public ImageFolder(String pathName, String folderName) {
        this.pathName = pathName;
        this.folderName = folderName;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public int getNumber_of_images() {
        return number_of_images;
    }

    public void setNumber_of_images(int number_of_images) {
        this.number_of_images = number_of_images;
    }

    public void addImages() {
        this.number_of_images++;
    }

    public String getImg_first() {
        return img_first;
    }

    public void setImg_first(String img_first) {
        this.img_first = img_first;
    }
}
