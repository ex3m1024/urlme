package me.url.api.model;

import java.util.Objects;

/**
 * A simple wrapper for user-submitted URLs
 * @author Nikita R-T
 */
public final class Url {

    private final String url;

    public Url() {
        this.url = "";
    }

    public Url(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Url url1 = (Url) o;
        return Objects.equals(url, url1.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }
}
