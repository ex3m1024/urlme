package me.url.api.services;

import me.url.api.model.ShortUrl;
import me.url.api.model.Url;
import me.url.api.model.UrlEntity;
import me.url.api.repository.UrlEntityRepository;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.zip.CRC32;

/**
 * @author Nikita R-T
 */
@Service
public class UrlService {

    private final UrlEntityRepository repository;

    private final UrlValidator urlValidator = new UrlValidator(new String[]{"http", "https", "ftp"});

    public UrlService(UrlEntityRepository repository) {
        this.repository = repository;
    }

    public List<UrlEntity> getAll() {
        return repository.findAll();
    }

    public boolean isSimpleUrlValid(Url url) {
        return urlValidator.isValid(url.getUrl());
    }

    public ShortUrl createShortURL(Url simpleUrl, String ip) throws Exception {
        CRC32 crc32 = new CRC32();
        crc32.update(simpleUrl.getUrl().getBytes(StandardCharsets.UTF_8));
        ShortUrl shortUrl = new ShortUrl(Long.toString(crc32.getValue(), 36).toUpperCase());
        UrlEntity entity = repository.save(new UrlEntity(simpleUrl.getUrl(), shortUrl.getCode(), ip));
        if (entity == null) throw new Exception("Unable to generate URL");
        else return shortUrl;
    }

    public String shortToSimpleUrl(String code) {
        UrlEntity urlEntity = repository.findByCode(code);
        if (urlEntity != null) return urlEntity.getOriginal();
        else return null;
    }

}
