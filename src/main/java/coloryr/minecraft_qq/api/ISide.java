package coloryr.minecraft_qq.api;

import coloryr.minecraft_qq.json.ReadObj;

public interface ISide {
    void message(ReadObj message);

    void send(Object sender, String message);
}
