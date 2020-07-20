package com.teamtter.elcleanator.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.teamtter.elcleanator.PomInfo;

public class RepositoryParser {

	private PomParser pomParser;

	private static final List<String> pomExtensions = List.of("xml", "pom");

	public RepositoryParser(PomParser pomparser) {
		this.pomParser = pomparser;
	}

	public Stream<PomInfo> fetchAllFrom(Path repositoryPath) throws IOException {
		return Files.walk(repositoryPath)
				//.parallel()
				.filter(Files::isRegularFile)
				.filter(path -> pomExtensions.contains(getExtension(path)))
				.map(pomPath -> convertPomToPomInfo(pomPath))
				.flatMap(Optional::stream);
	}

	private Optional<PomInfo> convertPomToPomInfo(Path pomPath) {
		Optional<PomInfo> pomInfo = pomParser.fetchPomInfo(pomPath);
		return pomInfo;
	}

	protected static String getExtension(Path path) {
		String fileName = path.getFileName().toString();
		String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
		return extension;
	}
}
