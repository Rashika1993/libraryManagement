package com.library;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.servlet.ServletContext;

@SpringBootTest
@WebAppConfiguration
class LibraryApplicationTests {
	@MockBean
	private ServletContext servletContext;
	@Test
	void contextLoads() {
	}

}
