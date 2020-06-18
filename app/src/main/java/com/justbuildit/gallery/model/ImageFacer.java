package com.justbuildit.gallery.model;

public class ImageFacer {
    private String image_Name;
    private String image_Path;
    private String image_Size;
    private String image_Uri;
    private Boolean selected = false;

    public ImageFacer() {
    }

    public ImageFacer(String image_Name, String image_Path, String image_Size, String image_Uri, Boolean selected) {
        this.image_Name = image_Name;
        this.image_Path = image_Path;
        this.image_Size = image_Size;
        this.image_Uri = image_Uri;
        this.selected = selected;
    }

    public String getImage_Name() {
        return image_Name;
    }

    public void setImage_Name(String image_Name) {
        this.image_Name = image_Name;
    }

    public String getImage_Path() {
        return image_Path;
    }

    public void setImage_Path(String image_Path) {
        this.image_Path = image_Path;
    }

    public String getImage_Size() {
        return image_Size;
    }

    public void setImage_Size(String image_Size) {
        this.image_Size = image_Size;
    }

    public String getImage_Uri() {
        return image_Uri;
    }

    public void setImage_Uri(String image_Uri) {
        this.image_Uri = image_Uri;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
