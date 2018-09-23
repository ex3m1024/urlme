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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Nikita R-T
 */
@RunWith(SpringRunner.class)
@WebMvcTest(MainController.class)
//@SpringBootTest(webEnvironment = MOCK)
public class ApiApplicationTests {

//	@Autowired private MainController controller;
	@Autowired private MockMvc mockMvc;
	@MockBean private UrlService urlService;
//	@Autowired private UrlService urlService;
	@MockBean private UrlEntityRepository urlEntityRepository;
	@Autowired private ObjectMapper objectMapper;

	@Test
	public void contextLoads() {
//		assertThat(controller).isNotNull();
	}

	@Test
	public void validUrlShouldReturnShortenedurl() throws Exception {
		// TODO: restructure

		Url testUrl = new Url("https://www.oracle.com");
		when(urlService.isSimpleUrlValid(testUrl)).thenReturn(true);
		ShortUrl aBcDeFg = new ShortUrl("aBcDeFg");
		when(urlService.simpleToShortUrl(testUrl)).thenReturn(aBcDeFg);
		UrlEntity entity = new UrlEntity(testUrl.getUrl(), "aBcDeFg", "127.0.0.1");
		when(urlEntityRepository.save(entity)).thenReturn(entity);
//		when(urlEntityRepository.save())
//		when(urlService.isSimpleUrlValid(new Url("googlecom"))).thenReturn(false);
		this.mockMvc
			.perform(post("/generate")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(new Url("https://www.oracle.com")))
					.header("Referer", "127.0.0.1")
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string(equalTo(objectMapper.writeValueAsString(aBcDeFg))));
	}

}
