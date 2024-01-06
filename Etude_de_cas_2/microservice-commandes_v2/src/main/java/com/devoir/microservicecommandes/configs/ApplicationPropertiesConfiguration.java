package com.devoir.microservicecommandes.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author azzai
 **/
@Component
@ConfigurationProperties("mes-configs-ms")
@RefreshScope
@Data
public class ApplicationPropertiesConfiguration {
private int commandes_last ;
}
