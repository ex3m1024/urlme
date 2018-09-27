package me.url.api.repository;

import me.url.api.model.IpStat;
import me.url.api.model.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Nikita R-T
 */
public interface UrlEntityRepository extends JpaRepository<UrlEntity, String> {

    UrlEntity findByCode(String code);

    @Query(" SELECT new me.url.api.model.IpStat(e.requestIp, COUNT(e.code))" +
            "FROM UrlEntity e " +
            "GROUP BY e.requestIp")
    List<IpStat> findEntityCountByIp();

}
