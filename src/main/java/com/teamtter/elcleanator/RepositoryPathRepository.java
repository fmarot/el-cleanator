package com.teamtter.elcleanator;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ListMultimap;
import com.teamtter.elcleanator.parser.RepositoryParser;

public class RepositoryPathRepository {

	private ListMultimap<MavenGAV, MavenGAV> parentGavToChildrenGav = ArrayListMultimap.create();
	private BiMap<MavenGAV, Path> gavToPath = HashBiMap.create();

	public RepositoryPathRepository(Path repoPath, RepositoryParser repoParser) throws IOException {
		repoParser.fetchAllFrom(repoPath).forEach(pomInfo -> addToRepo(pomInfo));
	}

	private void addToRepo(PomInfo pomInfo) {
		MavenGAV thisPomGAV = pomInfo.getThisPomGAV();
		parentGavToChildrenGav.put(pomInfo.getParentPomGAV(), thisPomGAV);
		gavToPath.put(thisPomGAV, pomInfo.getDirectoryPath());
	}

	public Stream<MavenGAV> fetchChildrenOf(MavenGAV parent) {
		
		List<MavenGAV> children = parentGavToChildrenGav.get(parent);
		
		return Stream.concat(
				Stream.of(parent),
				(parentGavToChildrenGav.get(parent)).stream()
						.flatMap(node2 -> fetchChildrenOf(node2)));
	}

}
