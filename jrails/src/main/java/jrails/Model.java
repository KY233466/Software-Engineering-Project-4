package jrails;

import java.util.*;
import java.lang.reflect.*;
import java.nio.file.*;
import java.io.*;

public class Model {

    public int instanceId = 0;
    public static HashMap<Class<?>, String> map = new HashMap<>();

    public void save() {
        try {
            int IDreserved = this.instanceId;
            System.out.println(IDreserved);
            String path = this.getClass().getName().toString() + ".csv";
            System.out.println(path);
            map.put(this.getClass(), path);

            List<List<String>> lines = new ArrayList<List<String>>();

            if (this.instanceId != 0) {
                lines = getLinesOtherThan(IDreserved, path);
            } else {
                lines = getAllLines(path);
            }

            Model.reset();

            System.out.println("     ");
            System.out.println("save");
            System.out.println(lines.size());

            String storedCount = lines.remove(0).get(0);
            storedCount = storedCount.replace("\"", "");

            String[] newCounts = new String[1];

            if (IDreserved != 0) {
                newCounts[0] = storedCount;
            } else {
                newCounts[0] = String.valueOf(Integer.parseInt(storedCount) + 1);
            }

            write(newCounts, path);

            int length = this.getClass().getDeclaredFields().length + 1;

            for (int i = 0; i < lines.size(); i++) {
                String[] data = processALine(lines.get(i).toString(), length);
                write(data, path);
            }

            Class<?> c = this.getClass();

            List<Object> listS = new ArrayList<Object>();

            Field[] fields = c.getDeclaredFields();
            for (Field classField : fields) {
                if (classField.isAnnotationPresent(Column.class)) {
                    Object value = classField.get(this);
                    if (value == null) {
                        listS.add("$$$NULS");
                    } else {
                        listS.add(value);
                    }
                }
            }

            String[] full = new String[listS.size() + 1];

            if (IDreserved != 0) {
                this.instanceId = IDreserved;
            } else {
                this.instanceId = Integer.parseInt(storedCount);
            }

            full[0] = String.valueOf(this.instanceId);

            int lengthF = full.length;

            for (int i = 1; i < lengthF; i++) {
                full[i] = listS.get(i - 1).toString();
            }

            write(full, path);
            System.out.println("     ");
        } catch (Exception e) {

        }
    }

    private static void write(String[] data, String path) {
        try {
            System.out.println("----write");
            System.out.println(Arrays.toString(data));
            // String path = this.getClass().toString() + ".csv";
            File csvFile = new File(path);
            FileWriter fileWriter = new FileWriter(csvFile, true);
            StringBuilder line = new StringBuilder();

            for (int i = 0; i < data.length; i++) {
                line.append("\"");
                line.append(data[i]);
                line.append("\"");
                if (i != data.length - 1) {
                    line.append(',');
                }
            }

            line.append("\n");

            fileWriter.write(line.toString());
            fileWriter.close();

        } catch (Exception e) {

        }
    }

    public int id() {
        return this.instanceId;
    }

    private static List<List<String>> getLine(int id_target, String path) {
        String file = path;
        String delimiter = "','";
        String line;
        List<List<String>> lines = new ArrayList<List<String>>();
        System.out.println("getLine:");
        System.out.println(id_target);
        System.out.println(path);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            // System.out.println("getLine:");
            line = br.readLine();
            System.out.println("count: ");
            System.out.println(line);

            while ((line = br.readLine()) != null) {
                System.out.println(line);

                if (line.substring(0, line.indexOf(',')).contains(String.valueOf(id_target))) {
                    System.out.println("^^^^found");
                    List<String> values = Arrays.asList(line.split(delimiter));
                    lines.add(values);
                }
            }
            System.out.println("    ");
            // System.out.println("-------");
            // System.out.println("     ");
        } catch (Exception e) {
            return null;
        }

        return lines;
    }

    public static <T> T find(Class<T> c, int id_T){
        try {
            System.out.println("    ");
            System.out.println("find:");
            Field[] fields = c.getFields();
            T o = c.getConstructor().newInstance();
            String path = c.getName().toString() + ".csv";
            System.out.println(path);
            System.out.println(id_T);
            
            List<List<String>> lines = getLine(id_T, path);

            if (lines == null || lines.size() == 0) {
                return null;
            }

            System.out.println("lines.size()");
            System.out.println(lines.size());
            List<Field> classes = new ArrayList<Field>();

            for (Field field : fields) {
                if (field.isAnnotationPresent(Column.class)) {
                    classes.add(field);
                }
                if (field.getName().toString() == "instanceId") {
                    classes.add(field);
                }
            }

            String[] data = processLine(lines.get(0).toString(), c.getDeclaredFields().length + 1);

            Object obj = buildObject(o, classes, data, c.getDeclaredFields().length);
            System.out.println("   ");
            return (T) obj;

        } catch (InstantiationException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IllegalArgumentException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (InvocationTargetException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (NoSuchMethodException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (SecurityException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        // return o;
        return null;
    }

    private static Object buildObject(Object o, List<Field> classes, String[] data, int length) {

        try {
            System.out.println("buildObject:");
            for (int i = 0; i < classes.size(); i++) {
                Field curr = classes.get(i);
                String name = curr.toString();

                if (name.contains("String")) {
                    if (data[i] != "$$$NULS") {
                        curr.set(o, data[i]);
                    } else {
                        curr.set(o, null);
                    }
                } else if (name.contains("int")) {
                    curr.set(o, Integer.valueOf(data[i]));
                } else if (name.contains("boolean")) {
                    curr.set(o, Boolean.parseBoolean(data[i]));
                }
            }

            return o;

        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return length;
    }

    private static String[] processLine(String s, int length) {

        String[] result = new String[length];
        System.out.println("processLine:");

        int start1 = s.indexOf("\",\"");
        String outStr = s.substring(start1 + 1, s.length());
        result[length - 1] = s.substring(2, start1);
        start1 = outStr.indexOf("\",\"");

        for (int i = 0; i < length - 2; i++) {
            String data = outStr.substring(2, start1);
            // System.out.println(data);
            result[i] = data;
            outStr = outStr.substring(start1 + 1, outStr.length());
            start1 = outStr.indexOf("\",\"");
        }

        String num = outStr.substring(2, outStr.length() - 2);
        result[length - 2] = num;
        // // System.out.println(Arrays.toString(result));

        return result;
    }

    private static List<List<String>> getAllLines(String path) {
        System.out.println("getAllLines");
        String file = path;
        String delimiter = "','";
        String line;
        List<List<String>> lines = new ArrayList<List<String>>();
        List<String> count = new ArrayList<String>();

        // reset();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            System.out.println("file exists");
            // make sure the first line is read without split
            // System.out.println("count");
            String bbbb = br.readLine();
            System.out.println(bbbb);
            // System.out.println("??");
            count.add(bbbb);
            lines.add(count);

            while ((line = br.readLine()) != null) {
                // System.out.println("while");
                System.out.println(line);
                List<String> values = Arrays.asList(line.split(delimiter));
                lines.add(values);
            }
            // System.out.println("-------");
            System.out.println("     ");
        } catch (Exception e) {
            File f = new File(file);
            try {
                f.createNewFile();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            System.out.println("file doesn't exist");
            String[] countS = new String[1];
            List<String> lol = new ArrayList<String>();
            lol.add("1");
            lines.add(lol);
            countS[0] = "1";
            write(countS, path);
        }

        return lines;
    }

    public static <T> List<T> all(Class<T> c) {
        try {
            System.out.println("all:");
            List<T> result = new ArrayList<T>();

            Field[] fields = c.getFields();

            String path = c.getName().toString() + ".csv";

            List<List<String>> lines = getAllLines(path);
            List<Field> classes = new ArrayList<Field>();

            for (Field field : fields) {
                if (field.isAnnotationPresent(Column.class)) {
                    classes.add(field);
                }
                if (field.getName().toString() == "instanceId") {
                    classes.add(field);
                }
            }

            int length = c.getDeclaredFields().length + 1;
            T o = c.getConstructor().newInstance();

            System.out.println("in all contains");
            // lines.remove(0);

            String storedCount = lines.remove(0).get(0);

            System.out.println(storedCount);

            for (int i = 0; i < lines.size(); i++) {
                // System.out.println("run");
                System.out.println(lines.get(i).toString());
                String[] data = processLine(lines.get(i).toString(), length);
                System.out.println(Arrays.toString(data));
                buildObject(o, classes, data, length);
                result.add(o);
                o = c.getConstructor().newInstance();
            }
            System.out.println("     ");

            return result;

        } catch (Exception e) {

        }
        return null;
    }

    private static List<List<String>> getLinesOtherThan(int id_target, String path) {
        System.out.println("getLinesOtherThan:");
        String file = path;
        String delimiter = "','";
        String line;
        List<List<String>> lines = new ArrayList<List<String>>();
        List<String> count = new ArrayList<String>();
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            line = br.readLine();
            System.out.println(line);
            count.add(line);
            lines.add(count);

            while ((line = br.readLine()) != null) {
                if (!line.substring(0, line.indexOf(',')).contains(String.valueOf(id_target))) {
                    List<String> values = Arrays.asList(line.split(delimiter));
                    lines.add(values);
                }
                else {
                    found = true;
                }
            }
        } catch (Exception e) {
            return null;
        }

        if (!found) {
            return null;
        }

        return lines;
    }

    private static String[] processALine(String s, int length) {
        System.out.println("processALine:");

        String[] result = new String[length];

        int start1 = s.indexOf("\",\"");
        String outStr = s.substring(start1 + 1, s.length());
        result[0] = s.substring(2, start1);
        start1 = outStr.indexOf("\",\"");

        for (int i = 1; i < length - 1; i++) {
            String data = outStr.substring(2, start1);
            // System.out.println(data);
            result[i] = data;
            outStr = outStr.substring(start1 + 1, outStr.length());
            start1 = outStr.indexOf("\",\"");
        }

        String num = outStr.substring(2, outStr.length() - 2);
        // System.out.println(num);
        result[length - 1] = num;
        // System.out.println(Arrays.toString(result));

        return result;
    }

    public void destroy() {
        System.out.println("destroy:");
        String path = this.getClass().getName().toString() + ".csv";

        if (this.instanceId == 0) {
            System.out.println("Raise exception");
            throw new UnsupportedOperationException();
        }

        try {
            List<List<String>> lines = getLinesOtherThan(this.instanceId, path);

            Model.reset();

            int length = this.getClass().getDeclaredFields().length + 1;

            String storedCount = lines.remove(0).get(0);
            System.out.println(storedCount);
            storedCount = storedCount.replace("\"", "");
            String[] newCounts = new String[1];
            newCounts[0] = storedCount;
            write(newCounts, path);

            for (int i = 0; i < lines.size(); i++) {
                String[] data = processALine(lines.get(i).toString(), length);
                write(data, path);
            }

            System.out.println("^^^done destroy");
            System.out.println("   ");

        } catch (Exception e) {

        }
    }

    public static void reset() {
        try {
            for (String path : map.values()) {
                File ff = new File(path);
                Files.deleteIfExists(ff.toPath());
            }
        } catch (IOException e) {
        }
    }
}
