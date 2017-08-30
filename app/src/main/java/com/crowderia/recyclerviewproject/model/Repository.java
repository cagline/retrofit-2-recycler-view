package com.crowderia.recyclerviewproject.model;

/**
 * Created by crowderia on 8/29/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static com.crowderia.recyclerviewproject.utilities.ApiUtils.IMAGE_URL;

public class Repository {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image1")
    @Expose
    private String image1;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("owner")
    @Expose
    private Owner owner;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage1() {
//        return IMAGE_URL.concat(image1);
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

}