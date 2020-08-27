package ru.profit.rocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.profit.rocket.util.logger.LoggingAnnotationProcessor;

@Configuration
public class LoggerConfig {
    @Bean
    public LoggingAnnotationProcessor loggingAnnotationProcessor(){
        return new LoggingAnnotationProcessor();
    }
}
