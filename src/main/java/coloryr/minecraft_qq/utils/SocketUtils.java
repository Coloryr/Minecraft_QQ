package coloryr.minecraft_qq.utils;

import coloryr.minecraft_qq.Minecraft_QQ;
import coloryr.minecraft_qq.api.Placeholder;
import coloryr.minecraft_qq.json.ReadObj;
import coloryr.minecraft_qq.json.SendObj;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SocketUtils {

    private static final Queue<ByteBuf> queueRead = new ConcurrentLinkedQueue<>();
    private static final Queue<ByteBuf> queueSend = new ConcurrentLinkedQueue<>();
    private static boolean isRun;
    private static boolean isConnect;
    private static int timeout;

    private static final NioEventLoopGroup group = new NioEventLoopGroup();
    private static final Bootstrap bootstrap = new Bootstrap();
    private static Channel client;

    public static void start() {
        bootstrap
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new LengthFieldPrepender(4))
                                .addLast(new LengthFieldBasedFrameDecoder(1024 * 8, 0, 4, 0, 4))
                                .addLast(new ClientHandler());
                    }
                });

        Thread readThread = new Thread(() -> {
            try {
                while (!isRun) {
                    Thread.sleep(100);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            int b = 0;
            while (isRun) {
                try {
                    if (!isConnect) {
                        if (timeout < 10) {
                            Minecraft_QQ.log.warning("§d[Minecraft_QQ]§5" + Minecraft_QQ.config.System.AutoConnectTime + "秒后重连");
                            try {
                                int a = Minecraft_QQ.config.System.AutoConnectTime;
                                while (isRun && a > 0) {
                                    Thread.sleep(1000);
                                    a--;
                                }
                            } catch (InterruptedException ignored) {

                            }
                            Minecraft_QQ.log.warning("§d[Minecraft_QQ]§5Minecraft_QQ_Cmd/Gui重连中");
                            reConnect();
                        } else if (timeout == 10) {
                            Minecraft_QQ.log.warning("§d[Minecraft_QQ]§5自动重连失败次数过多，输入/qq socket 来重置连接超时次数");
                            timeout = 12;
                        }
                    } else {
                        ByteBuf temp = queueRead.poll();
                        if (temp != null) {
                            if (Minecraft_QQ.config.System.Debug)
                                Minecraft_QQ.log.info("§d[Minecraft_QQ]§5[Debug]收到数据：" + temp);
                            ReadObj obj = PackDecode.ToObj(temp);
                            temp.release();
                            Minecraft_QQ.side.message(obj);
                        }

                        temp = queueSend.poll();
                        if (temp != null) {
                            client.writeAndFlush(temp);
                        }
                        b++;
                        if (b > 600) {
                            b = 0;
                            ByteBuf buff = Unpooled.buffer();
                            buff.writeInt(120);
                            queueSend.add(buff);
                        }
                    }
                    Thread.sleep(50);
                } catch (Exception e) {
                    Minecraft_QQ.log.warning("§d[Minecraft_QQ]§5Socket错误");
                    e.printStackTrace();
                    isConnect = false;
                }
            }
        });
        readThread.start();
        isRun = true;
    }

    private static void reConnect() {
        try {
            Minecraft_QQ.log.info("§d[Minecraft_QQ]§5正在连接Minecraft_QQ_Cmd/Gui");

            if(client!=null && client.isOpen()){
                client.close().sync();
            }

            client = bootstrap.connect(Minecraft_QQ.config.System.IP, Minecraft_QQ.config.System.Port).await().channel();
            if (client.isActive()) {
                Thread.sleep(200);
                queueRead.clear();
                queueSend.clear();
                ByteBuf pack = PackEncode.packStart(Minecraft_QQ.config.ServerSet.ServerName);
                client.writeAndFlush(pack);

                Minecraft_QQ.log.info("§d[Minecraft_QQ]§5Minecraft_QQ_Cmd/Gui已连接");
                isConnect = true;
            } else {
                Minecraft_QQ.log.info("§d[Minecraft_QQ]§cMinecraft_QQ_Cmd/Gui连接失败");
                timeout++;
            }
        } catch (Exception e) {
            Minecraft_QQ.log.info("§d[Minecraft_QQ]§cMinecraft_QQ_Cmd/Gui连接失败");
            timeout++;
            e.printStackTrace();
        }
    }

    public static void stop() {
        Minecraft_QQ.log.info("§d[Minecraft_QQ]§5连接已断开");
        isRun = false;
        if (client != null) {
            try{
                client.close().sync();
                group.shutdownGracefully().sync();
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }
        if (Minecraft_QQ.config.System.Debug)
            Minecraft_QQ.log.info("§d[Minecraft_QQ]§5[Debug]线程已关闭");
    }

    public static void sendData(String data, String group, String player, String message) {
        SendObj obj = new SendObj(data, group, player, message);
        ByteBuf pack = PackEncode.toPack(obj);
        socketSend(pack, player, message);
    }

    private static String build(String[] arg) {
        StringBuilder builder = new StringBuilder();
        for (int a = 1; a < arg.length; a++) {
            builder.append(arg[a]).append(" ");
        }
        return builder.toString();
    }

    public static void sendData(String data, String group, String player, String[] arg) {
        String message = build(arg);
        SendObj obj = new SendObj(data, group, player, message);
        ByteBuf pack = PackEncode.toPack(obj);
        socketSend(pack, player, message);
    }

    private static void socketSend(ByteBuf send, String Player, String message) {
        queueSend.add(send);
        if (Minecraft_QQ.config.Logs.Server) {
            Logs.logWrite("[Server]" + (Player == null ? "测试" : Player) + ":" + message);
        }
        if (Minecraft_QQ.config.System.Debug)
            Minecraft_QQ.log.info("§d[Minecraft_QQ]§5[Debug]发送数据：" + send);
    }

    public static void socketClose() {
        if (client != null && isConnect) {
            client.close();
        }
    }

    public static boolean isRun() {
        return isConnect;
    }

    public static void socketReset() {
        timeout = 0;
    }

    static class ClientHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelRead(ChannelHandlerContext context, Object message) {
            ByteBuf byteBuf = (ByteBuf) message;
            queueRead.add(byteBuf);
        }

        @Override
        public void channelInactive(ChannelHandlerContext context) {
            isConnect = false;
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext context) {
            context.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
            cause.printStackTrace();
            isConnect = false;
            context.close();
        }
    }
}
