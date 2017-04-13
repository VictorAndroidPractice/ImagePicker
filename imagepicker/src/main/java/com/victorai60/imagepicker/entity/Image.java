package com.victorai60.imagepicker.entity;

public class Image {
    private String path;
    private String name;
    private long date;

    public Image(String path, String name, long date) {
        this.path = path;
        this.name = name;
        this.date = date;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object obj) {
        try {
            Image image = (Image) obj;
            return path.equalsIgnoreCase(image.path);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return super.equals(obj);
    }
}
