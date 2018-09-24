package me.url.api.controller;

import me.url.api.model.ShortUrl;
import me.url.api.model.Url;
import me.url.api.services.UrlService;
import me.url.api.model.UrlEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Nikita R-T
 */
@RestController
public class MainController {

    private final UrlService service;

    public MainController(UrlService service) {
        this.service = service;
    }

    @GetMapping("/list")
    List<UrlEntity> all() {
        return service.getAll();
    }

    @PostMapping("/generate")
    ShortUrl create(@RequestBody Url url, HttpServletRequest request) throws Exception {
        if (service.isSimpleUrlValid(url)) {
            return service.createShortURL(url, request.getRemoteAddr());
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
    RedirectView resolve(@PathVariable(value="shortUrlCode") String code) {
        String originalURL = service.shortToSimpleUrl(code);
        if (originalURL == null) return new RedirectView("/");
        else return new RedirectView(originalURL);
    }

}
