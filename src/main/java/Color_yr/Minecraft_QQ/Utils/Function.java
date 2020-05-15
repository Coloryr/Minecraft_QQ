package Color_yr.Minecraft_QQ.Utils;

public class Function {
    public static String get_string(String a, String b, String c) {
        int x = a.indexOf(b) + b.length();
        int y = a.indexOf(c);
        char[] data = a.toCharArray();
        if (data[y - 1] == '"')
            y = a.indexOf(c, y + 1);
        return a.substring(x, y);
    }
}
