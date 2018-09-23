package me.url.api.controller;

import me.url.api.model.ShortUrl;
import me.url.api.model.Url;
import me.url.api.services.UrlService;
import me.url.api.model.UrlEntity;
import me.url.api.repository.UrlEntityRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Nikita R-T
 */
@RestController
public class MainController {

    private final UrlEntityRepository repository;
    private final UrlService service;

    public MainController(UrlEntityRepository repository, UrlService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping("/list")
    List<UrlEntity> all() {
        return repository.findAll();
    }

    @PostMapping("/generate")
    ShortUrl create(@RequestBody Url url, @RequestHeader("Referer") String referer) throws Exception {
        if (service.isSimpleUrlValid(url)) {
            ShortUrl shortUrl = service.simpleToShortUrl(url);
//            UrlEntity entity = repository.save(new UrlEntity(url.getUrl(), shortUrl.getFullUrl(), referer));
//            if (entity != null)
                return shortUrl;
//            else throw new Exception("Unable to generate URL");
        } else {
            throw new Exception("Invalid URL");
        }
    }

//    @PostMapping("/resolve")
//    Url resolve(@RequestParam String code) throws Exception {
//        if (code.equals("ex")) throw new Exception("Not found in database!");
//        return service.shortToSimpleUrl(code);
//    }

    @GetMapping("/{shortUrlCode}")
    Url resolve(@PathVariable(value="shortUrlCode") String code) {
        return service.shortToSimpleUrl(code);
    }

}
