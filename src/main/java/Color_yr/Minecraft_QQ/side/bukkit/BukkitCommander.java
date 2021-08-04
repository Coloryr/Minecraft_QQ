package Color_yr.Minecraft_QQ.side.bukkit;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class BukkitCommander implements CommandSender {
    public List<String> message = new ArrayList<>();
    public String player;

    @Override
    public void sendMessage(String message) {
        this.message.add(message);
    }

    @Override
    public void sendMessage(String[] messages) {
        message.addAll(Arrays.asList(messages));
    }

    @Override
    public void sendMessage(@Nullable UUID sender, @NotNull String message) {
        this.message.add(message);
    }

    @Override
    public void sendMessage(@Nullable UUID sender, @NotNull String... messages) {
        message.addAll(Arrays.asList(messages));
    }

    @Override
    public Server getServer() {
        return Bukkit.getServer();
    }

    @Override
    public String getName() {
        return player;
    }

    @Override
    public Spigot spigot() {
        return new Spigot() {
            @Override
            public void sendMessage(BaseComponent component) {
                message.add(component.toLegacyText());
            }

            @Override
            public void sendMessage(BaseComponent... components) {
                for (BaseComponent temp : components)
                    message.add(temp.toLegacyText());
            }
        };
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
        return Bukkit.getConsoleSender().addAttachment(plugin, name, value);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin) {
        return Bukkit.getConsoleSender().addAttachment(plugin);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value, int ticks) {
        return Bukkit.getConsoleSender().addAttachment(plugin, name, value, ticks);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, int ticks) {
        return Bukkit.getConsoleSender().addAttachment(plugin, ticks);
    }

    @Override
    public void removeAttachment(PermissionAttachment attachment) {

    }

    @Override
    public void recalculatePermissions() {

    }

    @Override
    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        return Bukkit.getConsoleSender().getEffectivePermissions();
    }

    @Override
    public boolean isOp() {
        return true;
    }

    @Override
    public void setOp(boolean value) {

    }
}
