package com.teamtter.elcleanator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.teamtter.elcleanator.parser.PomParser;
import com.teamtter.elcleanator.parser.RepositoryParser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RepositoryPathRepositoryTest {

	@Test
	public void fetch_children_recursively_of_parent() throws IOException {
		MavenGAV parent = new MavenGAV("groupId1", "parent", "1.0");
		Path repoPath = new File("./src/test/resources/repo1").toPath();
		RepositoryPathRepository repo = new RepositoryPathRepository(repoPath, new RepositoryParser(new PomParser()));
		List<MavenGAV> children = repo.fetchChildrenOf(parent).collect(Collectors.toList());
		assertEquals(4, children.size());
		
		// TODO recursively
	}
}
