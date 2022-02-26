package fr.vocaltech.fsm.server;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BasicControllerTest {
    @LocalServerPort
    private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void indexShouldReturnDefaultMessage() {
		String url = "http://localhost:" + port;
		assertThat(restTemplate.getForObject(url, String.class)).contains("ServerController works !");
	}

}
