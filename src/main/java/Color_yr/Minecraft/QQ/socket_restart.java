package Color_yr.Minecraft.QQ;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import Color_yr.Minecraft.QQ.Minecraft_QQ;

public class socket_restart {
	static boolean is_restart = false;

	public boolean socket_restart_start() {
		Minecraft_QQ.log.info("§d[Minecraft_QQ]§5正在连接酷Q");
		if (logs.Socket_log == true) {
			logs logs = new logs();
			logs.log_write("[socket]正在连接酷Q");
		}
		try {
			socket.socket = new Socket(Minecraft_QQ.System_IP, Minecraft_QQ.System_PORT);

			socket.socket_runFlag = true;
			Minecraft_QQ.log.info("§d[Minecraft_QQ]§5酷Q已连接");
			if (logs.Socket_log == true) {
				logs logs = new logs();
				logs.log_write("[socket]酷Q已连接");
			}
			is_restart = false;
			socket.socket_first = false;
			return true;
		} catch (UnknownHostException e) {
			Minecraft_QQ.log.warning("§d[Minecraft_QQ]§c酷Q连接失败");
			if (logs.Socket_log == true) {
				logs logs = new logs();
				logs.log_write("[socket]酷Q连接失败");
			}
			if (logs.Error_log == true) {
				logs logs = new logs();
				logs.log_write("[ERROR]" + e.getMessage());
			}
			is_restart = false;
			socket.socket_runFlag = false;
			socket.socket_first = false;
			return false;
		} catch (IOException e) {
			Minecraft_QQ.log.warning("§d[Minecraft_QQ]§c酷Q连接失败");
			if (logs.Socket_log == true) {
				logs logs = new logs();
				logs.log_write("[socket]酷Q连接失败");
			}
			if (logs.Error_log == true) {
				logs logs = new logs();
				logs.log_write("[ERROR]" + e.getMessage());
			}
			is_restart = false;
			socket.socket_runFlag = false;
			socket.socket_first = false;
			return false;
		}
	}

	public boolean restart_socket() {
		if (is_restart != true) {
			socket.socket_first = true;
			socket.socket_stop();
			socket.readThread = new socket();
			socket.readThread.start();
			return socket_restart_start();
		} else {
			Minecraft_QQ.log.warning("§d[Minecraft_QQ]§c酷Q连接失败");
			if (logs.Socket_log == true) {
				logs logs = new logs();
				logs.log_write("[socket]酷Q连接失败");
			}
			return false;
		}
	}
}
