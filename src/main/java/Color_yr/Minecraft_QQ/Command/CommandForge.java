package Color_yr.Minecraft_QQ.Command;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class CommandForge implements ICommand {

    private final List<String> aliases;

    public CommandForge() {
        aliases = new ArrayList<>();
        aliases.add("qq");
        aliases.add("QQ");
    }

    @Override
    public String getName() {
        return "Minecraft_QQ";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/qq help";
    }

    @Override
    public List<String> getAliases() {
        return aliases;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] string) {
        CommandEX.Ex(sender, sender.getName(), string, checkPermission(server, sender));
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return sender.canUseCommand(3, getName());
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] string, BlockPos targetPos) {
        return CommandTab.getList(checkPermission(server, sender), string);
    }

    @Override
    public boolean isUsernameIndex(String[] string, int number) {
        return number == 1 && string[0].equalsIgnoreCase("qq");
    }

    @Override
    public int compareTo(ICommand arg0) {
        return this.getName().compareTo(arg0.getName());
    }
}
