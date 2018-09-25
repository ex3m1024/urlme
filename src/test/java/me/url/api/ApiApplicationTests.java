package me.url.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.url.api.controller.MainController;
import me.url.api.model.ShortUrl;
import me.url.api.model.Url;
import me.url.api.model.UrlEntity;
import me.url.api.repository.UrlEntityRepository;
import me.url.api.services.UrlService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Nikita R-T
 */
@RunWith(SpringRunner.class)
@WebMvcTest(MainController.class)
//@SpringBootTest(webEnvironment = MOCK)
public class ApiApplicationTests {

	@Autowired private MockMvc mockMvc;
	@MockBean private UrlService urlService;
	@MockBean private UrlEntityRepository urlEntityRepository;
	@Autowired private ObjectMapper objectMapper;

	private static final String TEST_ADDRESS = "https://www.oracle.com";
	private static final String LOCALHOST_IP = "127.0.0.1";
	private static final Url testUrl = new Url(TEST_ADDRESS);
	private static final ShortUrl aBcDeFg = new ShortUrl("aBcDeFg");

	@Test
	public void contextLoads() {
		assertThat(urlService).isNotNull();
		assertThat(urlEntityRepository).isNotNull();
	}

	@Test
	public void validUrlShouldReturnShortenedUrl() throws Exception {
		// TODO: restructure
		when(urlService.isSimpleUrlValid(testUrl)).thenReturn(true);
		when(urlService.createShortURL(testUrl, LOCALHOST_IP)).thenReturn(aBcDeFg);
		UrlEntity entity = new UrlEntity("aBcDeFg", testUrl.getUrl(), LOCALHOST_IP);
		when(urlEntityRepository.save(entity)).thenReturn(entity);
		this.mockMvc
			.perform(post("/generate")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(new Url(TEST_ADDRESS)))
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string(equalTo(objectMapper.writeValueAsString(aBcDeFg))));
	}

	@Test
	public void convertedUrlShouldRedirectCorrectly() throws Exception {
		UrlEntity entity = new UrlEntity("aBcDeFg", testUrl.getUrl(), LOCALHOST_IP);
		when(urlService.shortToSimpleUrl(aBcDeFg.getCode())).thenReturn(entity.getOriginal());
		this.mockMvc
			.perform(get("/" + aBcDeFg.getCode()))
			.andExpect(redirectedUrl(TEST_ADDRESS));
	}
}
