package Color_yr.Minecraft_QQ.Socket;

public class socket_start {
    public void socket_start() {
        socket.hand.readThread = new socket();
        socket.hand.readThread.start();
        socket_restart socket_restart = new socket_restart();
        socket_restart.socket_restart_start();
    }
}
