package Color_yr.Minecraft_QQ.API;

public interface ISocketControl {
    void start();

    void close();

    void stop();

    boolean socketConnect();

    boolean sendData(String data, String group, String player, String message);

    void socketRestart();

    boolean isRun();
}
