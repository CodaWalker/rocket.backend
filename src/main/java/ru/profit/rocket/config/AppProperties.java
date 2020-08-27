package ru.profit.rocket.config;

import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class AppProperties {

    private static volatile AppProperties instance;

    private static Properties mailProperties;

    public static AppProperties getInstance() {
        if (instance == null) {
            synchronized (AppProperties.class) {
                if (instance == null) {
                    instance = new AppProperties();
                }
            }
        }
        return instance;
    }

    private AppProperties(){ }

    public String getMailProperties(String key) {
        return mailProperties.getProperty(key);
    }

    public void setMailProperty(String key, String value) {
        mailProperties.setProperty(key,value);
    }

    public void setMailProperties(Properties properties) {
        mailProperties = properties;
    }
}
