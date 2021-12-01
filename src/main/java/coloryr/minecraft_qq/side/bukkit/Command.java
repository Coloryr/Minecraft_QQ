package coloryr.minecraft_qq.side.bukkit;

import coloryr.minecraft_qq.command.CommandEX;
import coloryr.minecraft_qq.command.CommandTab;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.List;

public class Command implements CommandExecutor, TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("qq")) {
            CommandEX.ex(sender, sender.getName(), args, sender.isOp());
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("qq")) {
            return CommandTab.getList(sender.isOp(), args);
        }
        return null;
    }
}
