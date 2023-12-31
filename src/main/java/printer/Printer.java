package printer;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

/**
 * Класс для упрощения ввода/вывода текста
 */
public class Printer {
    public static String printTree(List<String> tree, String sep) {
        StringBuilder sb = new StringBuilder();
        for (Object item : tree) {
            sb.append(item);
            sb.append(sep);
        }
        return sb.toString();
    }

    public static String printList(List<Object> list, String sep) {
        StringBuilder sb = new StringBuilder();
        for (Object item : list) {
            sb.append(item.toString());
            sb.append(sep);
        }
        return sb.toString();
    }

    public static String printList(byte[] list, String sep) {
        StringBuilder sb = new StringBuilder();
        for (byte item : list) {
            sb.append(">");
            sb.append(item);
            sb.append(sep);
        }
        sb.append("\n").append(new String(list));
        return sb.toString();
    }

    public static String readFile(String file) {
        StringBuilder sb = new StringBuilder();
        try {
            FileReader fr = new FileReader(file);
            int c;
            while ((c=fr.read())!=-1) {
                sb.append((char)c);
            }
            fr.close();
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        return sb.toString();
    }

    public static void writeToFile(String file, String text, boolean append) {
        try {
            FileWriter fw = new FileWriter(file, append);
            fw.write(text);
            fw.close();
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}
