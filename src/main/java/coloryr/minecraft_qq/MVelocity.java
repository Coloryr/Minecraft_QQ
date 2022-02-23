package coloryr.minecraft_qq;

import coloryr.minecraft_qq.side.velocity.*;
import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

import java.nio.file.Path;

@Plugin(id = "minecraft_qq", name = "Minecraft_QQ", version = Minecraft_QQ.version,
        url = "https://github.com/HeartAge/Minecraft_QQ", description = "QQ群与服务器互联", authors = {"Color_yr"})
public class MVelocity {
    public static MVelocity plugin;
    public final ProxyServer server;
    public final Path dataDirectory;
    private final Logger logger;
    private final Metrics.Factory metricsFactory;

    @Inject
    public MVelocity(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory, Metrics.Factory metricsFactory) {
        this.server = server;
        this.logger = logger;
        this.dataDirectory = dataDirectory;
        this.metricsFactory = metricsFactory;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        plugin = this;
        Minecraft_QQ.log = new Log(logger);
        new Minecraft_QQ().init(dataDirectory.toFile());
        Minecraft_QQ.side = new Side();
        CommandMeta meta = server.getCommandManager().metaBuilder("qq")
                .aliases("minecraft_qq")
                .build();
        server.getCommandManager().register(meta, new Command());
        server.getEventManager().register(this, new EventListener());
        metricsFactory.make(this, 6608);

        Minecraft_QQ.start();
    }

    @Subscribe
    public void onStop(ProxyShutdownEvent event) {
        Minecraft_QQ.stop();
    }
}
