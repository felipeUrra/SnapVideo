package org.example;

public class Image {
    private String name;
    private String duration;
    private String extension;

    public Image(String name, String duration, String extension) {
        this.name = name;
        this.duration = duration;
        this.extension = extension;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
