package me.url.api;

import me.url.api.model.IpStat;
import me.url.api.model.UrlEntity;
import me.url.api.repository.UrlEntityRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Nikita R-T
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class RepositoryTests {

    @Autowired private UrlEntityRepository urlEntityRepository;

    @Test
    public void testIpStatsWithData() {
        // Persist 5 UrlEntities (unique codes, but IPs end with "0", "1", "2", "0" and "1" respectively, therefore,
        // some IPs have made several requests for URL shortening)
        List<UrlEntity> urlEntities = IntStream
                .range(0, 5)
                .mapToObj(i -> new UrlEntity("code" + i,
                        "http://example.com?arg=" + i,
                        "10.0.2." + (i % 3)))
                .collect(Collectors.toList());
        urlEntities.forEach(e -> urlEntityRepository.save(e));

        // Call IP statistics method, check that IPs are output correctly
        // and unique shortened IP count corresponds to the urlEntities list
        List<IpStat> statsList = urlEntityRepository.findEntityCountByIp();
        assertThat(statsList).containsExactlyInAnyOrder(
                new IpStat("10.0.2.0", 2),
                new IpStat("10.0.2.1", 2),
                new IpStat("10.0.2.2", 1)
        );
    }

    @Test
    public void testIpStatsWithoutData() {
        List<IpStat> statsList = urlEntityRepository.findEntityCountByIp();
        assertThat(statsList).isEmpty();
    }

}
