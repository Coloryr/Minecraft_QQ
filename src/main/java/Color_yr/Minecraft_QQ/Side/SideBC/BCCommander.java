package Color_yr.Minecraft_QQ.Side.SideBC;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class BCCommander implements CommandSender {
    public List<String> message = new ArrayList<String>();
    public String player;

    @Override
    public String getName() {
        return player;
    }

    @Override
    public void sendMessage(String message) {
        this.message.add(message);
    }

    @Override
    public void sendMessages(String... messages) {
        message.addAll(Arrays.asList(messages));
    }

    @Override
    public void sendMessage(BaseComponent... message) {
        this.message.add(Arrays.toString(message));
    }

    @Override
    public void sendMessage(BaseComponent message) {
        this.message.add(message.toLegacyText());
    }

    @Override
    public Collection<String> getGroups() {
        return null;
    }

    @Override
    public void addGroups(String... groups) {

    }

    @Override
    public void removeGroups(String... groups) {

    }

    @Override
    public boolean hasPermission(String permission) {
        return true;
    }

    @Override
    public void setPermission(String permission, boolean value) {

    }

    @Override
    public Collection<String> getPermissions() {
        return null;
    }
}
