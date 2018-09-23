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
    private String shortened;
    private String requestData;

    public UrlEntity() {}

    public UrlEntity(String original, String shortened, String requestData) {
        this.original = original;
        this.shortened = shortened;
        this.requestData = requestData;
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

    public String getShortened() {
        return shortened;
    }

    public void setShortened(String shortened) {
        this.shortened = shortened;
    }

    public String getRequestData() {
        return requestData;
    }

    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UrlEntity entity = (UrlEntity) o;
        return Objects.equals(original, entity.original) &&
                Objects.equals(shortened, entity.shortened) &&
                Objects.equals(requestData, entity.requestData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(original, shortened, requestData);
    }

    @Override
    public String toString() {
        return "UrlEntity{" +
                "id=" + id +
                ", original='" + original + '\'' +
                ", shortened='" + shortened + '\'' +
                ", requestData='" + requestData + '\'' +
                '}';
    }
}
