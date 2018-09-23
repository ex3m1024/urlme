package me.url.api.model;

import java.beans.Transient;

/**
 * @author Nikita R-T
 */
public class ShortUrl {

    private static final String PROJECTURLPREFIX = "http://url.me/";
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
        return PROJECTURLPREFIX + code;
    }
}
