package com.teamtter.elcleanator.repository.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MavenGAV {
	String	groupId;
	String	artifactId;
	String	version;

}
