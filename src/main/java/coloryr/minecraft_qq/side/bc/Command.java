package coloryr.minecraft_qq.side.bc;

import coloryr.minecraft_qq.command.CommandEX;
import coloryr.minecraft_qq.command.CommandTab;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.TabExecutor;

public class Command extends net.md_5.bungee.api.plugin.Command implements TabExecutor {

    public Command() {
        super("qq");
    }

    public void execute(CommandSender sender, String[] args) {
        CommandEX.ex(sender, sender.getName(), args, sender.hasPermission("Minecraft_QQ.admin"));
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return CommandTab.getList(sender.hasPermission("Minecraft_QQ.admin"), args);
    }
}
