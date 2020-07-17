package Color_yr.Minecraft_QQ.API;

public interface IMinecraft_QQ {
    void logInfo(String message);

    void message(String message);

    void logError(String message);

    void send(Object sender, String message);

    void run(Runnable runnable);
}
