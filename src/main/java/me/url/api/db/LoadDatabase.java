package me.url.api.db;

import me.url.api.repository.UrlEntityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Nikita R-T
 */
@Configuration
class LoadDatabase {

    /**
     * Execute custom stuff upon DB load, if needed
     * @param repository
     * @return
     */
    @Bean
    CommandLineRunner initDatabase(UrlEntityRepository repository) {
        return args -> {};
    }
}