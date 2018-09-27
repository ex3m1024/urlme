package me.url.api.model;

import java.beans.Transient;

/**
 * A wrapper for shortened URLs. Consists of the URL code, and can return the shortened URL with url.me prefix
 * @author Nikita R-T
 */
public class ShortUrl {

    private static final String PROJECT_URL_PREFIX = "http://url.me/";
    private final String code;

    public ShortUrl() {
        this.code = "";
    }

    public ShortUrl(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    /** Get full URL that can be redirected to, if needed
     * @return url
     */
    @Transient
    public String getFullUrl() {
        return PROJECT_URL_PREFIX + code;
    }
}
