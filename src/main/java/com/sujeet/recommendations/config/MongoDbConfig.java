package com.sujeet.recommendations.config;

import com.mongodb.MongoClientSettings;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import lombok.extern.slf4j.Slf4j;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoDatabase;
import com.sujeet.recommendations.repository.MoviesRepository;

@Configuration
@Slf4j
@EnableReactiveMongoRepositories(basePackageClasses = MoviesRepository.class)
public class MongoDbConfig extends AbstractReactiveMongoConfiguration {

    @Autowired
    private SecretProperties secretProperties;
    public static final String CREDENTIALS = "credentials";

    @Value("${mongodb.database}")
    private String dbName;

    @Value("${mongodb.url}")
    private String connectionstr;

    @Override
    protected String getDatabaseName() {
        return dbName;
    }

    @Bean
    @Primary
    public MongoClient reactiveMongoClient() {

        String credentials = secretProperties.getUsername() + ":" + secretProperties.getPassword();
        connectionstr = connectionstr.replace(CREDENTIALS, credentials);
        return MongoClients.create(connectionstr);
    }

    @Bean
    public MongoDatabase mongoDatabase() {
        CodecRegistry pojoCodecRegistry = org.bson.codecs.configuration.CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), org.bson.codecs.configuration.CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        return reactiveMongoClient().getDatabase(dbName).withCodecRegistry(pojoCodecRegistry);
    }

}
