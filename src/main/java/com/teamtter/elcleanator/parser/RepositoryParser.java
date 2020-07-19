package com.teamtter.elcleanator.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import com.teamtter.elcleanator.RepositoryPath;

public class RepositoryParser {

	private static final List<String> pomExtensions = List.of("xml", "pom");

	static Stream<RepositoryPath> fetchAllFrom(Path repositoryPath) throws IOException {
		return Files.walk(repositoryPath)
				.parallel()
				.filter(Files::isRegularFile)
				.filter(path -> pomExtensions.contains(getExtension(path)))
				.map(RepositoryParser::convertPomTorepositoryPath);
	}

	private static RepositoryPath convertPomTorepositoryPath(Path pomPath) {
		return new RepositoryPath();
	}

	protected static String getExtension(Path path) {
		String fileName = path.getFileName().toString();
		String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
		return extension;
	}
}
