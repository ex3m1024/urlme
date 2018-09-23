package me.url.api.services;

import me.url.api.model.ShortUrl;
import me.url.api.model.Url;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Service;

/**
 * @author Nikita R-T
 */
@Service
public class UrlService {

    private final UrlValidator urlValidator = new UrlValidator(new String[]{"http", "https", "ftp"});

    public boolean isSimpleUrlValid(Url url) {
        return urlValidator.isValid(url.getUrl());
    }

    public ShortUrl simpleToShortUrl(Url simpleUrl) {
        // TODO: implement algorithm
        return new ShortUrl(String.valueOf(simpleUrl.hashCode()));
    }

    public Url shortToSimpleUrl(String code) {
        // TODO: implement search
        return new Url("The URL resolves to: " + code);
    }

}
