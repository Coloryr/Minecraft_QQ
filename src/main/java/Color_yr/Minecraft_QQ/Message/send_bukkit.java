package Color_yr.Minecraft_QQ.Message;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class send_bukkit {

    private static List<String> message = new ArrayList<String>();
    private String player;

    public void setPlayer(String player) {
        this.player = player;
    }

    public List<String> getMessage() {
        return message;
    }

    public void clear() {
        player = null;
        message.clear();
    }

    public CommandSender sender = new CommandSender() {

        @Override
        public void sendMessage(String message) {
            send_bukkit.message.add(message);
        }

        @Override
        public void sendMessage(String[] messages) {
            send_bukkit.message.addAll(Arrays.asList(messages));
        }

        @Override
        public Server getServer() {
            return org.bukkit.Bukkit.getServer();
        }

        @Override
        public String getName() {
            return player;
        }

        @Override
        public Spigot spigot() {
            return null;
        }

        @Override
        public boolean isPermissionSet(String name) {
            return true;
        }

        @Override
        public boolean isPermissionSet(Permission perm) {
            return true;
        }

        @Override
        public boolean hasPermission(String name) {
            return true;
        }

        @Override
        public boolean hasPermission(Permission perm) {
            return true;
        }

        @Override
        public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value) {
            return org.bukkit.Bukkit.getConsoleSender().addAttachment(plugin, name, value);
        }

        @Override
        public PermissionAttachment addAttachment(Plugin plugin) {
            return org.bukkit.Bukkit.getConsoleSender().addAttachment(plugin);
        }

        @Override
        public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value, int ticks) {
            return org.bukkit.Bukkit.getConsoleSender().addAttachment(plugin, name, value, ticks);
        }

        @Override
        public PermissionAttachment addAttachment(Plugin plugin, int ticks) {
            return org.bukkit.Bukkit.getConsoleSender().addAttachment(plugin, ticks);
        }

        @Override
        public void removeAttachment(PermissionAttachment attachment) {

        }

        @Override
        public void recalculatePermissions() {

        }

        @Override
        public Set<PermissionAttachmentInfo> getEffectivePermissions() {
            return org.bukkit.Bukkit.getConsoleSender().getEffectivePermissions();
        }

        @Override
        public boolean isOp() {
            return true;
        }

        @Override
        public void setOp(boolean value) {

        }
    };
}
