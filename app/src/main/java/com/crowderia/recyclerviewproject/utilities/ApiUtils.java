package com.crowderia.recyclerviewproject.utilities;

import com.crowderia.recyclerviewproject.service.PropertyService;
import com.crowderia.recyclerviewproject.service.RetrofitClient;

/**
 * Created by crowderia on 8/29/17.
 */

public class ApiUtils {
    public static final String BASE_URL = "https://api.github.com";
    public static final String IMAGE_URL = "http://back-office.grt-property.lk/storage/app/";

    public static PropertyService getPropertyService() {
        return RetrofitClient.getClient(BASE_URL).create(PropertyService.class);
    }
}
