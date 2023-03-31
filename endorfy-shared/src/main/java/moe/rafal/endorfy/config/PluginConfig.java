package moe.rafal.endorfy.config;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;

@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {

    public String jdbcUri = "jdbc:mysql://127.0.0.1:3306/endorfy";

    public String username = "shitzuu";

    public String password = "my-little-unicorn-123-!@#";
}
