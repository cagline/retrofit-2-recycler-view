package com.crowderia.recyclerviewproject.model;

/**
 * Created by crowderia on 8/29/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static com.crowderia.recyclerviewproject.utilities.Constants.IMAGE_URL;

public class Property {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image1")
    @Expose
    private String image1;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage1() {
        return IMAGE_URL.concat(image1);
//        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

}