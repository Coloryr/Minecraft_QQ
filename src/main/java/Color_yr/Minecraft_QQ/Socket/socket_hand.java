package Color_yr.Minecraft_QQ.Socket;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class socket_hand {
    public PrintWriter pw = null;
    public OutputStream os = null;
    public Socket socket = null;
    public InputStream is = null;

    public boolean socket_runFlag = false;
    public boolean server_isclose = false;
    public boolean socket_stop = false;
    public boolean socket_first = true;

    public byte[] buf = new byte[1024];

    public socket readThread = null;
}
