package com.crowderia.recyclerviewproject;

/**
 * Created by crowderia on 8/28/17.
 */

public class ListItem {
    private String head;
    private String desc;
    private String imageUrl;

    public static String imageBaseUrl = "http://back-office.grt-property.lk/storage/app/";

    public ListItem(String head, String desc, String imageUrl) {
        this.head = head;
        this.desc = desc;
        this.imageUrl = imageUrl;
    }

    public String getHead() {
        return head;
    }

    public String getDesc() {
        return desc;
    }

    public String getImageUrl() {
        return imageBaseUrl.concat(imageUrl);
    }
}
