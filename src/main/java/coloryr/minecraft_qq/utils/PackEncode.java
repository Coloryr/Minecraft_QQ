package coloryr.minecraft_qq.utils;

import coloryr.minecraft_qq.json.SendObj;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.StandardCharsets;

public class PackEncode {
    public static void writeString(ByteBuf buf, String data) {
        if (data == null)
            return;
        byte[] temp = data.getBytes(StandardCharsets.UTF_8);
        buf.writeInt(temp.length);
        buf.writeBytes(temp);
    }

    public static ByteBuf toPack(SendObj obj){
        ByteBuf buff = Unpooled.buffer();
        buff.writeInt(1);
        writeString(buff, obj.group);
        writeString(buff, obj.message);
        writeString(buff, obj.player);
        writeString(buff, obj.data);

        return buff;
    }

    public static ByteBuf packStart(String name)
    {
        ByteBuf buff = Unpooled.buffer();
        buff.writeInt(0);
        writeString(buff, name);

        return buff;
    }
}
