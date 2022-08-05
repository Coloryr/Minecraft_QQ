package coloryr.minecraft_qq.utils;

import coloryr.minecraft_qq.json.ReadObj;
import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;

public class PackDecode {
    public static String readString(ByteBuf buff) {
        int length = buff.readInt();
        byte[] temp = new byte[length];
        buff.readBytes(temp);
        return new String(temp, StandardCharsets.UTF_8);
    }

    public static ReadObj ToObj(ByteBuf buff) {
        ReadObj obj = new ReadObj();
        obj.group = readString(buff);
        obj.message = readString(buff);
        obj.player = readString(buff);
        obj.command = readString(buff);
        obj.isCommand = buff.readBoolean();
        return obj;
    }
}
