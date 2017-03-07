package com.antonenko.xenia.racoons.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.antonenko.xenia.racoons.entities.Racoon;
import com.antonenko.xenia.racoons.repositories.RacoonRepository;

@RestController
public class RacoonController {

	@Autowired
	private RacoonRepository racoonRepository;

	@RequestMapping(value = "/racoon/", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Racoon>> getRacoons() {
		List<Racoon> racoons = racoonRepository.findAll();
		if (racoons.isEmpty()) {
			return new ResponseEntity<List<Racoon>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Racoon>>(racoons, HttpStatus.OK);
	}

	@RequestMapping(value = "/racoon/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Racoon> getRacoon(@PathVariable long id) {

		Racoon racoon = racoonRepository.findOne(id);
		if (racoon == null) {
			System.out.println("Racoon with id " + id + " not found");
			return new ResponseEntity<Racoon>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Racoon>(racoon, HttpStatus.OK);
	}

	@RequestMapping(value = "/racoon/", method = RequestMethod.POST)
	public ResponseEntity<Void> createRacoon(@RequestBody Racoon racoon, UriComponentsBuilder ucBuilder) {

		racoon = racoonRepository.save(racoon);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/racoon/{id}").buildAndExpand(racoon.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/racoon/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Racoon> updateRacoon(@PathVariable("id") long id, @RequestBody Racoon racoon) {
		System.out.println("Updating racoon " + id);

		Racoon currentRacoon = racoonRepository.findOne(id);

		if (currentRacoon == null) {
			System.out.println("Racoon with id " + id + " not found");
			return new ResponseEntity<Racoon>(HttpStatus.NOT_FOUND);
		}

		currentRacoon.setName(racoon.getName());
		currentRacoon.setWeight(racoon.getWeight());
		racoonRepository.save(currentRacoon);
		return new ResponseEntity<Racoon>(currentRacoon, HttpStatus.OK);
	}

	@RequestMapping(value = "/racoon/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Racoon> deleteRacoon(@PathVariable("id") long id) {
		System.out.println("Deleting racoon with id " + id);

		Racoon racoon = racoonRepository.findOne(id);
		if (racoon == null) {
			System.out.println("Unable to delete. Racoon with id " + id + " not found");
			return new ResponseEntity<Racoon>(HttpStatus.NOT_FOUND);
		}

		racoonRepository.delete(id);
		return new ResponseEntity<Racoon>(HttpStatus.NO_CONTENT);
	}
}
