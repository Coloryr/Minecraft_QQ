package Color_yr.Minecraft_QQ.Side.SideVelocity;

import Color_yr.Minecraft_QQ.Minecraft_QQVelocity;
import com.velocitypowered.api.network.ProtocolVersion;
import com.velocitypowered.api.permission.Tristate;
import com.velocitypowered.api.proxy.ConnectionRequestBuilder;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.messages.ChannelIdentifier;
import com.velocitypowered.api.proxy.player.PlayerSettings;
import com.velocitypowered.api.proxy.player.TabList;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import com.velocitypowered.api.util.GameProfile;
import com.velocitypowered.api.util.MessagePosition;
import com.velocitypowered.api.util.ModInfo;
import com.velocitypowered.api.util.title.Title;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.TextComponent;
import net.kyori.text.Component;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class VelocityCommander implements Player {
    public List<String> message = new ArrayList<String>();
    private String player;
    private Player play;
    private boolean havePlayer;

    public VelocityCommander(String player) {
        this.player = player;
        havePlayer = Minecraft_QQVelocity.plugin.server.getPlayer(player).isPresent();
        if (havePlayer) {
            play = Minecraft_QQVelocity.plugin.server.getPlayer(player).get();
        }
    }

    public List<String> getMessage() {
        return message;
    }

    @Override
    public String getUsername() {
        return player;
    }

    @Override
    public UUID getUniqueId() {
        if (havePlayer) {
            return play.getUniqueId();
        }
        return null;
    }

    @Override
    public Optional<ServerConnection> getCurrentServer() {
        if (havePlayer)
            return play.getCurrentServer();
        return Optional.empty();
    }

    @Override
    public PlayerSettings getPlayerSettings() {
        if (havePlayer)
            return play.getPlayerSettings();
        return null;
    }

    @Override
    public Optional<ModInfo> getModInfo() {
        if (havePlayer)
            return play.getModInfo();
        return Optional.empty();
    }

    @Override
    public long getPing() {
        if (havePlayer)
            return play.getPing();
        return 0;
    }

    @Override
    public boolean isOnlineMode() {
        if (havePlayer)
            return play.isOnlineMode();
        return false;
    }

    @Override
    public void sendMessage(Component component) {
        if (component instanceof TextComponent) {
            TextComponent obj = (TextComponent) component;
            message.add(obj.content());
        } else if (component instanceof net.kyori.text.TextComponent) {
            net.kyori.text.TextComponent obj = (net.kyori.text.TextComponent) component;
            message.add(obj.content());
        }
    }

    @Override
    public void sendMessage(Component component, MessagePosition position) {
        if (component instanceof TextComponent) {
            TextComponent obj = (TextComponent) component;
            message.add(obj.content());
        } else if (component instanceof net.kyori.text.TextComponent) {
            net.kyori.text.TextComponent obj = (net.kyori.text.TextComponent) component;
            message.add(obj.content());
        }
    }

    @Override
    public ConnectionRequestBuilder createConnectionRequest(RegisteredServer server) {
        if (havePlayer)
            return play.createConnectionRequest(server);
        return null;
    }

    @Override
    public List<GameProfile.Property> getGameProfileProperties() {
        if (havePlayer)
            return play.getGameProfileProperties();
        return null;
    }

    @Override
    public void setGameProfileProperties(List<GameProfile.Property> properties) {
        if (havePlayer)
            play.setGameProfileProperties(properties);
    }

    @Override
    public GameProfile getGameProfile() {
        if (havePlayer)
            return play.getGameProfile();
        return null;
    }

    @Override
    public void setHeaderAndFooter(Component header, Component footer) {
        if (havePlayer)
            play.setHeaderAndFooter(header, footer);
    }

    @Override
    public void clearHeaderAndFooter() {
        if (havePlayer)
            play.clearHeaderAndFooter();
    }

    @Override
    public net.kyori.adventure.text.Component getPlayerListHeader() {
        if (havePlayer)
            play.getPlayerListHeader();
        return null;
    }

    @Override
    public net.kyori.adventure.text.Component getPlayerListFooter() {
        if (havePlayer)
            play.getPlayerListFooter();
        return null;
    }

    @Override
    public TabList getTabList() {
        if (havePlayer)
            play.getTabList();
        return null;
    }

    @Override
    public void disconnect(Component reason) {
        if (havePlayer)
            play.disconnect(reason);
    }

    @Override
    public void disconnect(net.kyori.adventure.text.Component reason) {
        if (havePlayer)
            play.disconnect(reason);
    }

    @Override
    public void sendTitle(Title title) {
        if (havePlayer)
            play.sendTitle(title);
    }

    @Override
    public void spoofChatInput(String input) {
        if (havePlayer)
            play.spoofChatInput(input);
    }

    @Override
    public void sendResourcePack(String url) {
        if (havePlayer)
            play.sendResourcePack(url);
    }

    @Override
    public void sendResourcePack(String url, byte[] hash) {
        if (havePlayer)
            play.sendResourcePack(url, hash);
    }

    @Override
    public Tristate getPermissionValue(String permission) {
        return Tristate.TRUE;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        if (havePlayer)
            return play.getRemoteAddress();
        return null;
    }

    @Override
    public Optional<InetSocketAddress> getVirtualHost() {
        if (havePlayer)
            return play.getVirtualHost();
        return Optional.empty();
    }

    @Override
    public boolean isActive() {
        if (havePlayer)
            return play.isActive();
        return false;
    }

    @Override
    public ProtocolVersion getProtocolVersion() {
        if (havePlayer)
            return play.getProtocolVersion();
        return null;
    }

    @Override
    public boolean sendPluginMessage(ChannelIdentifier identifier, byte[] data) {
        if (havePlayer)
            return play.sendPluginMessage(identifier, data);
        return false;
    }

    @Override
    public @NonNull Identity identity() {
        if (havePlayer)
            return play.identity();
        return Identity.nil();
    }
}
