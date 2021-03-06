package me.url.api.model;

//import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

/**
 * @author Nikita R-T
 */
@Entity
public class UrlEntity {

    private @Id String code;
    private String original;
    private String requestIp;

    public UrlEntity() {}

    public UrlEntity(String code, String original, String requestIp) {
        this.code = code;
        this.original = original;
        this.requestIp = requestIp;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getCode() {
        return code;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UrlEntity entity = (UrlEntity) o;
        return Objects.equals(original, entity.original) &&
                Objects.equals(code, entity.code) &&
                Objects.equals(requestIp, entity.requestIp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(original, code, requestIp);
    }

    @Override
    public String toString() {
        return "UrlEntity{" +
                "code='" + code + '\'' +
                ", original='" + original + '\'' +
                ", requestIp='" + requestIp + '\'' +
                '}';
    }
}