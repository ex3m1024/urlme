package me.url.api;

import me.url.api.model.ShortUrl;
import me.url.api.model.Url;
import me.url.api.model.UrlEntity;
import me.url.api.repository.UrlEntityRepository;
import me.url.api.services.UrlService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Nikita R-T
 */
@RunWith(SpringRunner.class)
public class ServiceTests {

    @MockBean private UrlEntityRepository urlEntityRepository;

    @Test
    public void contextLoads() {
        assertThat(urlEntityRepository).isNotNull();
    }

    @Test
    public void shortUrlsAreCreated() throws Exception {
        UrlService urlService = new UrlService(urlEntityRepository);
        when(urlEntityRepository.save(any(UrlEntity.class))).thenReturn(new UrlEntity("", "", ""));
        assertThat(urlService.createShortURL(new Url("https://www.oracle.com"), "127.0.0.1"))
                .isEqualToComparingFieldByField(new ShortUrl("17FDF2W"));
        assertThat(urlService.createShortURL(new Url("https://www.oracle.com/cloud/applications.html"), "127.0.0.1"))
                .isEqualToComparingFieldByField(new ShortUrl("1089ERX"));
        assertThat(urlService.createShortURL(new Url("http://www.oracle.com/test?a=1&b=some%20test%20text!"), "127.0.0.1"))
                .isEqualToComparingFieldByField(new ShortUrl("RF0HWV"));
        assertThat(urlService.createShortURL(new Url("http://www.oracle.com/test?a=1&b=some%20test%20text!"), "0.0.0.0"))
                .isEqualToComparingFieldByField(new ShortUrl("RF0HWV"));
    }

    @Test
    public void addressesAreValidatedCorrectly() {
        UrlService urlService = new UrlService(urlEntityRepository);
        assertThat(urlService.isSimpleUrlValid(new Url("https://www.google.com"))).isEqualTo(true);
        assertThat(urlService.isSimpleUrlValid(new Url("https://google.com"))).isEqualTo(true);
        assertThat(urlService.isSimpleUrlValid(new Url("http://www.google.com"))).isEqualTo(true);
        assertThat(urlService.isSimpleUrlValid(new Url("http://google.com"))).isEqualTo(true);

        assertThat(urlService.isSimpleUrlValid(new Url("htt://www.google.com"))).isEqualTo(false);
        assertThat(urlService.isSimpleUrlValid(new Url("htt://www.google"))).isEqualTo(false);
        assertThat(urlService.isSimpleUrlValid(new Url("htt://www.com"))).isEqualTo(false);
        assertThat(urlService.isSimpleUrlValid(new Url("google.com"))).isEqualTo(false);
        assertThat(urlService.isSimpleUrlValid(new Url("www.google.com"))).isEqualTo(false);
    }

    @Test
    public void addressResolvingWorks() {
        UrlService urlService = new UrlService(urlEntityRepository);
        when(urlEntityRepository.findByCode("1089ERX")).thenReturn(new UrlEntity("1089ERX",
                "https://www.oracle.com/cloud/applications.html", "127.0.0.1"));
        when(urlEntityRepository.findByCode("IORRIO")).thenReturn(new UrlEntity("IORRIO",
                "http://github.com", "127.0.0.1"));
        assertThat(urlService.shortToSimpleUrl("1089ERX"))
                .isEqualTo("https://www.oracle.com/cloud/applications.html");
        assertThat(urlService.shortToSimpleUrl("IORRIO")).isEqualTo("http://github.com");
        assertThat(urlService.shortToSimpleUrl("")).isNullOrEmpty();
        assertThat(urlService.shortToSimpleUrl("nonExistingCode")).isNullOrEmpty();
    }

    @Test
    public void findAllWorks() {
        UrlService urlService = new UrlService(urlEntityRepository);
        UrlEntity u1 = new UrlEntity("aaa", "https://google.com", "10.0.1.15");
        UrlEntity u2 = new UrlEntity("bbb", "https://microsoft.com", "10.0.1.25");
        List<UrlEntity> list = new ArrayList<UrlEntity>() {{ add(u1); add(u2); }};
        when(urlEntityRepository.findAll()).thenReturn(list);

		assertThat(urlService.getAll()).containsOnly(u1, u2);
    }

    @Test
    public void ipStatsAreCorrect() {
        UrlService urlService = new UrlService(urlEntityRepository);
//        List<UrlEntity> list = new ArrayList<>();
        List<UrlEntity> collect = IntStream
                .range(1, 100)
                .mapToObj(i -> new UrlEntity("code" + i,
                        "http://example.com?arg=" + i,
                        "10.0.2." + (i % 5)))
                .collect(Collectors.toList());
//        System.out.println(collect);
        collect.forEach(e -> urlEntityRepository.save(e));
        System.out.println(urlEntityRepository.findAll());
//        when(urlEntityRepository.findEntityCountByIp())

//    map(e -> new UrlEntity("code" + , e, ("10.0.2." + (i % 5))))
//        Arrays.setAll(list, i -> new UrlEntity("code" + i, "", ("10.0.2." + (i % 5))));
    }
}
