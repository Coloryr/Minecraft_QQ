package Color_yr.Minecraft_QQ.command;

import java.util.ArrayList;
import java.util.List;

public class CommandTab {
    public static List<String> getList(boolean hasPermission, String[] args) {
        ArrayList<String> arguments = new ArrayList<>();
        arguments.add("chat");
        if (hasPermission) {
            if (args.length != 0 && args[0].equalsIgnoreCase("reload")) {
                arguments.add("config");
                arguments.add("socket");
            } else {
                arguments.add("help");
                arguments.add("say");
                arguments.add("reload");
            }
        }
        return arguments;
    }
}
