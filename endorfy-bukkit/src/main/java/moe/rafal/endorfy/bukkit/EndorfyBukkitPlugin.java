package moe.rafal.endorfy.bukkit;

import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import moe.rafal.endorfy.bukkit.listener.PlayerJoinListener;
import moe.rafal.endorfy.config.ConfigFactory;
import moe.rafal.endorfy.config.PluginConfig;
import moe.rafal.endorfy.datasource.DatasourceSpecification;
import moe.rafal.endorfy.datasource.PooledDatasource;
import moe.rafal.endorfy.datasource.PooledDatasourceHikari;
import moe.rafal.endorfy.facade.EndorfyFacade;
import moe.rafal.endorfy.facade.EndorfyFacadeImpl;
import moe.rafal.endorfy.mapping.MappingRepository;
import moe.rafal.endorfy.mapping.MappingService;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;

import static moe.rafal.endorfy.config.ConfigConstants.PLUGIN_CONFIG_FILE_NAME;

public class EndorfyBukkitPlugin extends JavaPlugin {

    private PooledDatasource datasource;

    @Override
    public void onEnable() {
        ConfigFactory configFactory = new ConfigFactory(getDataFolder(), YamlBukkitConfigurer::new);

        PluginConfig pluginConfig = configFactory.produceConfig(PluginConfig.class, PLUGIN_CONFIG_FILE_NAME);

        datasource = new PooledDatasourceHikari(new DatasourceSpecification(pluginConfig.jdbcUri,
            pluginConfig.username,
            pluginConfig.password));

        MappingRepository mappingRepository = new MappingRepository(datasource);
        mappingRepository.createSchema();

        MappingService mappingService = new MappingService(mappingRepository);

        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerJoinListener(mappingService), this);

        ServicesManager servicesManager = getServer().getServicesManager();
        servicesManager.register(EndorfyFacade.class, new EndorfyFacadeImpl(mappingService), this, ServicePriority.Normal);
    }

    @Override
    public void onDisable() {
        datasource.ditchConnections();
    }
}
