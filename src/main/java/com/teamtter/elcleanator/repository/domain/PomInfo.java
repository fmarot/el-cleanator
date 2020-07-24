package com.teamtter.elcleanator.repository.domain;

import java.nio.file.Path;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
public class PomInfo {
	Path directoryPath;

	// GAV stands for GroupId/ArtifactId/Version
	
	MavenGAV thisPomGAV;
	MavenGAV parentPomGAV;
}
