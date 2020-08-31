package com.teamtter.elcleanator.repository.services;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ListMultimap;
import com.teamtter.elcleanator.repository.domain.MavenGAV;
import com.teamtter.elcleanator.repository.domain.PomInfo;

public class RepositoryPathRepository {

	private ListMultimap<MavenGAV, MavenGAV> parentGavToChildrenGav = ArrayListMultimap.create();
	private Path repoPath;
	private RepositoryParser repoParser;

	private BiMap<MavenGAV, Path> gavToPath = HashBiMap.create();

	private AntPathMatcher matcher = new AntPathMatcher();

	public RepositoryPathRepository(Path repoPath, RepositoryParser repoParser) {
		this.repoPath = repoPath;
		this.repoParser = repoParser;
	}

	public void scan() throws IOException {
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

	public List<MavenGAV> findAllMatching(MavenGAV artifactToMatch) {
		return gavToPath.keySet().stream()
				.filter(gav -> matches(artifactToMatch, gav))
				.collect(Collectors.toList());
	}

	boolean matches(MavenGAV matcher, MavenGAV gav1) {
		return gavSubMatch(matcher.getGroupId(), gav1.getGroupId())
				&& gavSubMatch(matcher.getArtifactId(), gav1.getArtifactId())
				&& gavSubMatch(matcher.getVersion(), gav1.getVersion());
	}

	private boolean gavSubMatch(String matcherString, String toMatch) {
		if (StringUtils.isEmpty(matcherString)) {
			return true;
		}
		boolean match = matcher.match(matcherString, toMatch);
		return match;
	}

}
