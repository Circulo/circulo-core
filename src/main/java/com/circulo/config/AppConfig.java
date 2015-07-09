package com.circulo.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

/**
 * Created by tfulton on 5/23/15.
 */
@Configuration
public class AppConfig extends AbstractMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        return "circulo";   //@TODO : Make it config driven
    }

    @Bean
    public Mongo mongo() throws Exception {
        return new MongoClient("127.0.0.1");    //@TODO : Make it config driven
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(), getDatabaseName());
    }

    @Bean
    public GridFsTemplate gridFsTemplate() throws Exception {
        return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());
    }
}
