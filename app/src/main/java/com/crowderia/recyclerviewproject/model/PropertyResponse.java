package com.crowderia.recyclerviewproject.model;

/**
 * Created by crowderia on 8/29/17.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PropertyResponse {

    @SerializedName("data")
    @Expose
    private List<Property> data = null;

    public List<Property> getData() {
        return data;
    }

    public void setData(List<Property> data) {
        this.data = data;
    }

}