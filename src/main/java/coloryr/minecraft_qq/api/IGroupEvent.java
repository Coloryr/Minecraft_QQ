package coloryr.minecraft_qq.api;

public interface IGroupEvent {
    String getGroup();
    String getMessage();
    String getPlayer();
    String getCommand();
    boolean isCommand();
}
