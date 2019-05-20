package com.mye.mine;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MineApplication {

//    @Bean
//    public static PropertySourcesPlaceholderConfigurer properties() {
//        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
//        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
////        yaml.setResources(new FileSystemResource("config.yml"));//File引入
//        yaml.setResources(new ClassPathResource("application-watch.yml"));//class引入
//        configurer.setProperties(yaml.getObject());
//
//        return configurer;
//    }
    public static void main(String[] args) {
        SpringApplication.run(MineApplication.class, args);
    }

}
