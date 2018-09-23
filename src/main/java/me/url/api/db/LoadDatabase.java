package me.url.api.db;

//import lombok.extern.slf4j.Slf4j;

import me.url.api.model.UrlEntity;
import me.url.api.repository.UrlEntityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Nikita R-T
 */
@Configuration
//@Slf4j
class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(UrlEntityRepository repository) {
        return args -> {
            UrlEntity testUrl = new UrlEntity("google.com", "url.me/gDuJXopKp", "127.0.0.1");
            System.out.println("Preloading " + repository.save(testUrl));
//            System.out.println(repository.findAll().get(0).toString());
        };
    }
}