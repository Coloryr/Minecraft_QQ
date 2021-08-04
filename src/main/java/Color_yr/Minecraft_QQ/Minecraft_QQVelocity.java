package Color_yr.Minecraft_QQ;

import Color_yr.Minecraft_QQ.side.velocity.CommandVelocity;
import Color_yr.Minecraft_QQ.side.velocity.EventVelocity;
import Color_yr.Minecraft_QQ.side.velocity.MetricsVelocity;
import Color_yr.Minecraft_QQ.side.velocity.SideVelocity;
import Color_yr.Minecraft_QQ.side.velocity.VelocityLog;
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

@Plugin(id = "minecraft_qq", name = "Minecraft_QQ", version = Minecraft_QQ.Version,
        url = "https://github.com/HeartAge/Minecraft_QQ", description = "QQ群与服务器互联", authors = {"Color_yr"})
public class Minecraft_QQVelocity {
    public static Minecraft_QQVelocity plugin;
    public final ProxyServer server;
    public final Path dataDirectory;
    private final Logger logger;
    private final MetricsVelocity.Factory metricsFactory;

    @Inject
    public Minecraft_QQVelocity(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory, MetricsVelocity.Factory metricsFactory) {
        this.server = server;
        this.logger = logger;
        this.dataDirectory = dataDirectory;
        this.metricsFactory = metricsFactory;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        plugin = this;
        Minecraft_QQ.log = new VelocityLog(logger);
        new Minecraft_QQ().init(dataDirectory.toFile());
        Minecraft_QQ.Side = new SideVelocity();
        CommandMeta meta = server.getCommandManager().metaBuilder("qq")
                // Specify other aliases (optional)
                .aliases("minecraft_qq")
                .build();
        server.getCommandManager().register(meta, new CommandVelocity());
        server.getEventManager().register(this, new EventVelocity());
        metricsFactory.make(this, 6608);

        Minecraft_QQ.start();
    }

    @Subscribe
    public void onStop(ProxyShutdownEvent event) {
        Minecraft_QQ.stop();
    }
}
