package coloryr.minecraft_qq.side.velocity;

import coloryr.minecraft_qq.MVelocity;
import coloryr.minecraft_qq.command.CommandEX;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;

import java.util.concurrent.ExecutionException;

public class Command implements SimpleCommand {
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
            Commander send = new Commander("test");
            try {
                MVelocity.plugin.server.getCommandManager().executeAsync(send, "music help").get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return;
        }
        CommandEX.ex(source, name, args, invocation.source().hasPermission("Minecraft_QQ.admin"));
    }

    @Override
    public boolean hasPermission(final Invocation invocation) {
        return true;
    }
}