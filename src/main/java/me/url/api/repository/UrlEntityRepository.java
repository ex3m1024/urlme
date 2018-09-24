package me.url.api.repository;

import me.url.api.model.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Nikita R-T
 */
public interface UrlEntityRepository extends JpaRepository<UrlEntity, Long> {

    UrlEntity findByCode(String code);

}
