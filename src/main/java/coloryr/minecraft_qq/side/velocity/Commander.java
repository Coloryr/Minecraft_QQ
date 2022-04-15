package coloryr.minecraft_qq.side.velocity;

import coloryr.minecraft_qq.MVelocity;
import com.velocitypowered.api.network.ProtocolVersion;
import com.velocitypowered.api.permission.Tristate;
import com.velocitypowered.api.proxy.ConnectionRequestBuilder;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.messages.ChannelIdentifier;
import com.velocitypowered.api.proxy.player.PlayerSettings;
import com.velocitypowered.api.proxy.player.ResourcePackInfo;
import com.velocitypowered.api.proxy.player.TabList;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import com.velocitypowered.api.util.GameProfile;
import com.velocitypowered.api.util.ModInfo;
import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;

import java.net.InetSocketAddress;
import java.util.*;

public class Commander implements Player {
    public List<String> message = new ArrayList<String>();
    public String player;
    public Player play;
    private final boolean havePlayer;

    public Commander(String player) {
        this.player = player;
        havePlayer = MVelocity.plugin.server.getPlayer(player).isPresent();
        if (havePlayer) {
            play = MVelocity.plugin.server.getPlayer(player).get();
        }
    }

    @Override
    public String getUsername() {
        return player;
    }

    @Override
    public @Nullable Locale getEffectiveLocale() {
        if (havePlayer) {
            return play.getEffectiveLocale();
        }
        return null;
    }

    @Override
    public void setEffectiveLocale(Locale locale) {
        if (havePlayer) {
            play.setEffectiveLocale(locale);
        }
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
    public void sendMessage(@NonNull Identity identity, net.kyori.adventure.text.@NotNull Component message1, @NonNull MessageType type) {
        net.kyori.adventure.text.TextComponent obj = (net.kyori.adventure.text.TextComponent) message1;
        message.add(obj.content());
    }

    @Override
    public void sendMessage(@NotNull Component component) {
        if (component instanceof TextComponent) {
            TextComponent obj = (TextComponent) component;
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
    public void disconnect(net.kyori.adventure.text.Component reason) {
        if (havePlayer)
            play.disconnect(reason);
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
    public void sendResourcePackOffer(ResourcePackInfo packInfo) {
        if (havePlayer)
            play.sendResourcePackOffer(packInfo);
    }

    @Override
    public @Nullable ResourcePackInfo getAppliedResourcePack() {
        if (havePlayer)
            return play.getAppliedResourcePack();
        return null;
    }

    @Override
    public @Nullable ResourcePackInfo getPendingResourcePack() {
        if (havePlayer)
            return play.getPendingResourcePack();
        return null;
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
    public String getClientBrand() {
        if (havePlayer)
            return play.getClientBrand();
        return null;
    }

    @Override
    public @NonNull Identity identity() {
        if (havePlayer)
            return play.identity();
        return Identity.nil();
    }
}
