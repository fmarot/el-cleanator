package com.teamtter.elcleanator.repository.services;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import org.apache.maven.model.Model;
import org.apache.maven.model.Parent;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import com.teamtter.elcleanator.repository.domain.MavenGAV;
import com.teamtter.elcleanator.repository.domain.PomInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PomParser {

	private MavenXpp3Reader reader = new MavenXpp3Reader();

	public Optional<PomInfo> fetchPomInfo(Path pomPath) {
		Model model;
		try {
			model = reader.read(new FileReader(pomPath.toFile()));
		} catch (Exception e) {
			log.error("", e);
			return Optional.empty();
		}
		Parent parent = model.getParent();
		
		MavenGAV thisGav = new MavenGAV(
				model.getGroupId() == null ? parent.getGroupId() : model.getGroupId(),
				model.getArtifactId(),
				model.getVersion() == null ? parent.getVersion() : model.getVersion());
		MavenGAV parentGav = (parent == null ? null
				: new MavenGAV(parent.getGroupId(),
						parent.getArtifactId(),
						parent.getVersion()));

		PomInfo pomInfo = new PomInfo(pomPath.getParent(), thisGav, parentGav);
		return Optional.of(pomInfo);
	}

}
