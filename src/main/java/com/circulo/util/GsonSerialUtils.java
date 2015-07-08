package com.circulo.util;

import com.google.gson.Gson;

/**
 * Created by azim on 7/7/15.
 */
public class GsonSerialUtils {
    private static Gson gson;

    static {
        gson = new Gson();
    }

    public static String serializeObject(Object o) {
        String serializedObject = gson.toJson(o);
        return serializedObject;
    }

    public static Object deserializeObject(String s, Object o){
        Object object = gson.fromJson(s, o.getClass());
        return object;
    }

    public static Object cloneObject(Object o){
        String s = serializeObject(o);
        Object object = deserializeObject(s, o);
        return object;
    }
}
