package jrails;

import java.io.File;
import java.lang.reflect.*;
import java.nio.file.Files;
import java.util.*;

public class JRouter {

    public static HashMap<List<String>, List<Object>> map = new HashMap<>();

    public void addRoute(String verb, String path, Class clazz, String method) {
        List<Object> data = new ArrayList<>();
        List<String> key = new ArrayList<>();
        key.add(verb);
        key.add(path);

        System.out.println("addRoute");
        System.out.println(verb);
        System.out.println(path);
        System.out.println(clazz.getName());
        System.out.println(method);
        System.out.println("   ");

        data.add(clazz.getName());
        data.add(method);

        map.put(key, data);
    }

    // Returns "clazz#method" corresponding to verb+URN
    // Null if no such route
    public String getRoute(String verb, String path) {

        System.out.println("   ");
        System.out.println("getRoute");
        System.out.println(verb);
        System.out.println(path);
        System.out.println("   ");
        List<String> key = new ArrayList<>();
        key.add(verb);
        key.add(path);

        List<Object> target = map.get(key);

        if (target == null) {
            return null;
        }

        String result = target.get(0).toString() + "#" + target.get(1).toString();

        return result;
    }

    // Call the appropriate controller method and
    // return the result
    public Html route(String verb, String path, Map<String, String> params) {
        String result = getRoute(verb, path);

        if (result == null) {
            throw new UnsupportedOperationException();
        }

        try {

            System.out.println("not null");
            // File ff = new File("data.csv");
            // Files.deleteIfExists(ff.toPath());

            String[] data = result.split("#");

            Class<?> c = Class.forName(data[0]);
            String method = data[1];

            // System.out.println(c.getName());
            // System.out.println(method);
            // System.out.println("----");

            // Class<?> parameterType = params.getClass();

            Method target = c.getMethod(method, Map.class);

            Object o = c.getConstructor().newInstance();

            Object html = target.invoke(o, params);

            System.out.println("^^done route");

            return (Html) html;

        } catch (Exception e) {

        }
        return null;
    }
}
