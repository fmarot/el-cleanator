package com.teamtter.elcleanator;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;

public class RepositoryPathRepositoryTest {

	@Test
	public void fetch_children_recursively_of_parent() {
		RepositoryPath parent = new RepositoryPath("groupId1", "artifactId1", "1.0");
		Path repoPath = new File("./src/test/resources/repo1").toPath();
		RepositoryPathRepository repo = new RepositoryPathRepository(repoPath);
		List<RepositoryPath> children = repo.fetchChildrenOf(parent);
		
		// children should be found but also great-children and so on...
		TODO
		
		
	}
}
