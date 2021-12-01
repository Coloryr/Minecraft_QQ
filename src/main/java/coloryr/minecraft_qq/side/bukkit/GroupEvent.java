package coloryr.minecraft_qq.side.bukkit;

import coloryr.minecraft_qq.api.IGroupEvent;
import coloryr.minecraft_qq.json.ReadOBJ;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class GroupEvent extends Event implements IGroupEvent {
    private static final HandlerList handlers = new HandlerList();
    private String group;
    private String message;
    private String player;
    private boolean isCommand;
    private String command;

    public GroupEvent(ReadOBJ obj) {
        this.group = obj.group;
        this.message = obj.message;
        this.player = obj.player;
        this.isCommand = obj.isCommand;
        this.command = obj.command;
    }

    @Override
    public String getGroup() {
        return group;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getPlayer() {
        return player;
    }

    @Override
    public String getCommand() {
        return command;
    }

    @Override
    public boolean isCommand() {
        return isCommand;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
