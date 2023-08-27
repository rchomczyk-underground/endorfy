package moe.rafal.endorfy;

import com.google.inject.Inject;
import com.velocitypowered.api.event.EventManager;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import eu.okaeri.configs.yaml.snakeyaml.YamlSnakeYamlConfigurer;
import moe.rafal.endorfy.config.ConfigFactory;
import moe.rafal.endorfy.config.PluginConfig;
import moe.rafal.endorfy.datasource.DatasourceSpecification;
import moe.rafal.endorfy.datasource.PooledDatasource;
import moe.rafal.endorfy.datasource.PooledDatasourceHikari;
import moe.rafal.endorfy.mapping.MappingController;
import moe.rafal.endorfy.mapping.MappingRepository;
import moe.rafal.endorfy.mapping.MappingService;
import moe.rafal.endorfy.listener.PostLoginListener;

import java.nio.file.Path;

import static moe.rafal.endorfy.config.ConfigConstants.PLUGIN_CONFIG_FILE_NAME;

@Plugin(id = "endorfy", version = PomData.PLUGIN_VERSION, authors = "shitzuu <hello@rafal.moe>")
public class EndorfyVelocityPlugin {

    private final PluginConfig pluginConfig;
    private final ProxyServer proxyServer;
    private PooledDatasource datasource;

    @Inject
    public EndorfyVelocityPlugin(@DataDirectory Path dataPath, ProxyServer proxyServer) {
        this.proxyServer = proxyServer;
        ConfigFactory configFactory = new ConfigFactory(dataPath.toFile(), YamlSnakeYamlConfigurer::new);
        this.pluginConfig = configFactory.produceConfig(PluginConfig.class, PLUGIN_CONFIG_FILE_NAME);
    }

    @Subscribe
    public void onProxyStartup(ProxyInitializeEvent event) {
        initializeDriver();

        datasource = new PooledDatasourceHikari(new DatasourceSpecification(pluginConfig.jdbcUri,
            pluginConfig.username,
            pluginConfig.password));

        MappingRepository mappingRepository = new MappingRepository(datasource);
        mappingRepository.createSchema();

        MappingService mappingService = new MappingService(mappingRepository);
        MappingController mappingController = new MappingController(mappingService);

        EventManager eventManager = proxyServer.getEventManager();
        eventManager.register(this, new PostLoginListener(mappingController));

        EndorfyAccessor.setCurrentMappingService(mappingService);
    }

    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event) {
        datasource.ditchConnections();
    }

    private void initializeDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ignored) { }
    }
}