package com.cfw.plugins.parents.util;

import com.google.gson.Gson;

/**
 * Created by Duskrain on 2017/8/26.
 */
public class GsonUtil {
    private static Gson gson = new Gson();

    public static Gson getGson(){
        return gson;
    }
}
