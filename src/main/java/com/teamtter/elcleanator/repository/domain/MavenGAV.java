package com.teamtter.elcleanator.repository.domain;

import lombok.Value;

@Value
public class MavenGAV {
	String groupId;
	String artifactId;
	String versionId;
}
