package me.url.api.model;

//import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

/**
 * @author Nikita R-T
 */
//@Data
@Entity
public class UrlEntity {

    private @Id @GeneratedValue Long id;
    private String original;
    private String code;
    private String requestIp;

    public UrlEntity() {}

    public UrlEntity(String original, String code, String requestIp) {
        this.original = original;
        this.code = code;
        this.requestIp = requestIp;
    }

    public Long getId() {
        return id;
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

    public void setCode(String code) {
        this.code = code;
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
                "id=" + id +
                ", original='" + original + '\'' +
                ", shortened='" + code + '\'' +
                ", requestIp='" + requestIp + '\'' +
                '}';
    }
}
