package com.teamtter.elcleanator.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.teamtter.elcleanator.RepositoryPath;

public class RepositoryParserTest {

	@Test
	public void should_find_all_the_pom() throws IOException {
		Stream<RepositoryPath> allRepoPaths = RepositoryParser.fetchAllFrom(new File("./src/test/resources/repo1").toPath());
		assertEquals(3, allRepoPaths.count(), "all poms should be found");
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
