package com.teamtter.elcleanator.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.teamtter.elcleanator.repository.domain.MavenGAV;
import com.teamtter.elcleanator.repository.services.RepositoryPathRepository;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ArtifactsController {

	private RepositoryPathRepository	repository;

	public ArtifactsController(RepositoryPathRepository repository) {
		this.repository = repository;
	}

	@PostMapping("/findArtifacts")
	public ModelAndView greeting(MavenGAV artifactToMatch) {
		List<MavenGAV> matchingArtifacts = repository.findAllMatching(artifactToMatch);
		return new ModelAndView("artifacts", "artifacts", matchingArtifacts);
	}

	@GetMapping("/")
	public ModelAndView index() {
		log.info("index()");
		return new ModelAndView("index", "artifact", new MavenGAV("", "", ""));
	}

}
