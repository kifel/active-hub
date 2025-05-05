package br.com.cefet.activehub.utils;

public class ServletUtils {

    public static int extractIdFromPath(String path) {
        return Integer.parseInt(path.split("/")[1]);
    }

    public static int extractIdFromEditPath(String path) {
        return Integer.parseInt(path.split("/")[1]); // ex: "/123/edit"
    }

    public static int extractIdFromDeletePath(String path) {
        return Integer.parseInt(path.split("/")[1]); // ex: "/123/delete"
    }

    public static int extractIdFromTogglePath(String path) {
        return Integer.parseInt(path.split("/")[1]); // ex: "/123/toggle"
    }
}
