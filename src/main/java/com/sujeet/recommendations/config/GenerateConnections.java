package com.sujeet.recommendations.config;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import io.netty.channel.ChannelOption;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import io.netty.resolver.DefaultAddressResolverGroup;
import lombok.extern.slf4j.Slf4j;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.transport.logging.AdvancedByteBufFormat;
import com.sujeet.recommendations.connectors.models.Connection;
import com.sujeet.recommendations.connectors.models.ConnectionConfig;

import jakarta.annotation.PostConstruct;

@Configuration
@Slf4j
public class GenerateConnections implements BeanFactoryAware {

    private BeanFactory beanFactory;
    @Autowired
    private ConnectionRegistry connectionRegistry;

    @Override
    public void setBeanFactory(BeanFactory beanFactory)  {
        this.beanFactory = beanFactory;
    }

    @PostConstruct
    public void onInitialize() {
        if(Objects.isNull(connectionRegistry)) {
            throw new RuntimeException("Could not initialize connections please check application.yml file");
        }
        ConfigurableBeanFactory configurableBeanFactory = (ConfigurableBeanFactory) beanFactory;
        connectionRegistry.getClientConfigs()
                .forEach((clientName, config)->{
                    var webClient = beanFactory.getBean(WebClient.Builder.class);
                    var connection = generateConnection(webClient, config);
                    var beanName = clientName+"ConnectionConfig";
                    configurableBeanFactory.registerSingleton(beanName, connection);
                    log.info("Created an instance of bean for {}", beanName);
                });
    }

    private Connection generateConnection(WebClient.Builder webClientBuilder, ConnectionConfig config) {
        webClientBuilder.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        return Connection.builder()
                .webClient(webClientBuilder.clone()
                        .baseUrl(config.getHost()).build())
                .connectionConfigs(config)
                .build();
    }

}
