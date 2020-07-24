package com.teamtter.elcleanator.repository.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.teamtter.elcleanator.repository.domain.PomInfo;

public class RepositoryParserTest {

	@Test
	public void should_find_all_the_pom() throws IOException {
		Stream<PomInfo> pomInfos = new RepositoryParser(new PomParser()).fetchAllFrom(new File("./src/test/resources/repo1").toPath());
		assertEquals(4, pomInfos.count(), "all poms should be found");
	}

	@Test
	public void testGetExtension() throws Exception {
		Path p = null;
		String extension = null;

		p = Path.of("aa", "bb.com");
		extension = RepositoryParser.getExtension(p);
		assertEquals("com", extension);
		p = Path.of("aa", "bb");	// when no extension...
		extension = RepositoryParser.getExtension(p);
		assertEquals("bb", extension);	// ... the filename as is should be returned
	}
}
