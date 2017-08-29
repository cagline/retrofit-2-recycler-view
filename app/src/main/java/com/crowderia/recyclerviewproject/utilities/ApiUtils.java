package com.crowderia.recyclerviewproject.utilities;

import com.crowderia.recyclerviewproject.service.PropertyService;
import com.crowderia.recyclerviewproject.service.RetrofitClient;

/**
 * Created by crowderia on 8/29/17.
 */

public class ApiUtils {
    public static final String BASE_URL = "http://api.grt-property.lk";

    public static PropertyService getPropertyService() {
        return RetrofitClient.getClient(BASE_URL).create(PropertyService.class);
    }
}
