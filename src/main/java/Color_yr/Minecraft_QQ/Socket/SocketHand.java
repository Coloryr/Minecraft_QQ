package Color_yr.Minecraft_QQ.Socket;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketHand {

    public PrintWriter pw = null;
    public OutputStream os = null;
    public Socket socket = null;
    public InputStream is = null;

    public boolean socketIsRun = false;
    public boolean server_isclose = false;
    public boolean socket_stop = false;

    public byte[] buf = new byte[4096];

    public socketRead readThread = null;
}
