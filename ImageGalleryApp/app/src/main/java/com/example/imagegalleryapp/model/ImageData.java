package com.example.imagegalleryapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "ImageData")
public class ImageData implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "thumbnailUrl")
    public String thImageURL;

    @ColumnInfo(name = "title")
    public String imageTitle;

    @ColumnInfo(name = "url")
    public String imageURL;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getThImageURL() {
        return thImageURL;
    }

    public void setThImageURL(String thImageURL) {
        this.thImageURL = thImageURL;
    }

    public String getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
