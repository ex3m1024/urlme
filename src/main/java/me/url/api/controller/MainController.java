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

    /**
     * Get all currently registered URLs (code, original, requestIp)
     */
    @GetMapping("/list")
    List<UrlEntity> all() {
        return service.getAll();
    }

    /**
     * Shorten a URL
     * @param url String with a valid URL
     * @param request
     * @return Shortened URL code, if the specified URL is valid.
     * If the URL was converted before that, the code is still returned
     * @throws Exception
     */
    @PostMapping("/generate")
    ShortUrl create(@RequestBody Url url, HttpServletRequest request) throws Exception {
        if (service.isSimpleUrlValid(url)) {
            return service.createShortURL(url, request.getRemoteAddr());
        } else {
            throw new Exception("Invalid URL");
        }
    }

    /**
     * Redirect the user to the original URL if a valid existing short URL is specified.
     * If not, the user is redirected to / (homepage).
     * Example usage: url.me/17FDF2W will redirect to https://www.oracle.com
     * @param code
     * @return
     */
    @GetMapping("/{shortUrlCode}")
    RedirectView resolve(@PathVariable(value="shortUrlCode") String code) {
        String originalURL = service.shortToSimpleUrl(code);
        if (originalURL == null) return new RedirectView("/");
        else return new RedirectView(originalURL);
    }

}
