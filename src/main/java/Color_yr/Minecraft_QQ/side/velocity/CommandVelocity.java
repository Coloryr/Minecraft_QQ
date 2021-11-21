package Color_yr.Minecraft_QQ.side.velocity;

import Color_yr.Minecraft_QQ.Minecraft_QQVelocity;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;

import Color_yr.Minecraft_QQ.command.CommandEX;

import java.util.concurrent.ExecutionException;

public class CommandVelocity implements SimpleCommand {
    @Override
    public void execute(final Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();
        String name = "CONSOLE";
        if (invocation.source() instanceof Player) {
            Player player = (Player) invocation.source();
            name = player.getUsername();
        }
        if (args[0].equalsIgnoreCase("test")) {
            VelocityCommander send = new VelocityCommander("test");
            try {
                Minecraft_QQVelocity.plugin.server.getCommandManager().executeAsync(send, "music help").get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return;
        }
        CommandEX.Ex(source, name, args, invocation.source().hasPermission("Minecraft_QQ.admin"));
    }

    @Override
    public boolean hasPermission(final Invocation invocation) {
        return true;
    }
}