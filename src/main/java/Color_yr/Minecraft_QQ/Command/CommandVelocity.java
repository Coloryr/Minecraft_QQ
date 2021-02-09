package Color_yr.Minecraft_QQ.Command;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;

public class CommandVelocity implements SimpleCommand {
    @Override
    public void execute(final Invocation invocation) {
        CommandSource source = invocation.source();
        // Get the arguments after the command alias
        String[] args = invocation.arguments();
        String name = "CONSOLE";
        if (invocation.source() instanceof Player) {
            Player player = (Player) invocation.source();
            name = player.getUsername();
        }
        CommandEX.Ex(source, name, args, invocation.source().hasPermission("Minecraft_QQ.admin"));
    }

    @Override
    public boolean hasPermission(final Invocation invocation) {
        return true;
    }
}