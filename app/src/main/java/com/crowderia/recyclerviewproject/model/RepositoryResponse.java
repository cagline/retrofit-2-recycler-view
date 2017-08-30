package com.crowderia.recyclerviewproject.model;

/**
 * Created by crowderia on 8/29/17.
 */

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RepositoryResponse {

    @SerializedName("items")
    @Expose
    private List<Repository> data = null;

    public List<Repository> getData() {
        return data;
    }

    public void setData(List<Repository> data) {
        this.data = data;
    }

}