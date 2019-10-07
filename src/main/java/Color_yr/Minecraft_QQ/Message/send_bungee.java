package Color_yr.Minecraft_QQ.Message;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

class send_bungee {
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
        public String getName() {
            return player;
        }

        @Override
        public void sendMessage(String message) {
            send_bungee.message.add(message);
        }

        @Override
        public void sendMessages(String... messages) {
            send_bungee.message.addAll(Arrays.asList(messages));
        }

        @Override
        public void sendMessage(BaseComponent... message) {
            send_bungee.message.add(Arrays.toString(message));
        }

        @Override
        public void sendMessage(BaseComponent message) {
            send_bungee.message.add(message.toLegacyText());
            //send_bungee.message.add(message.toPlainText());
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
    };
}
