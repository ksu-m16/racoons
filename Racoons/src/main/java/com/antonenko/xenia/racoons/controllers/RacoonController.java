package com.antonenko.xenia.racoons.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.antonenko.xenia.racoons.entities.Racoon;
import com.antonenko.xenia.racoons.repositories.RacoonRepository;



@RestController
public class RacoonController {
	
	@Autowired
	private RacoonRepository racoonRepository;

	    
		@RequestMapping(value="/racoons", method=RequestMethod.GET, produces = "application/json")
		public @ResponseBody List<Racoon> getRacoons() {
			
			List<Racoon> racoons = new ArrayList<>();
			
			racoons.add(new Racoon("Sonya", 13));
			racoons.add(new Racoon("Cory", 10));
			racoons.add(new Racoon("Letho", 9));
			
			racoonRepository.save(racoons);
			
			
			return racoonRepository.findAll();
		}
	    
	    @RequestMapping(value="/racoon/{id}", method=RequestMethod.GET, produces = "application/json")
		public @ResponseBody Racoon getRacoon(@PathVariable long id) {
			return racoonRepository.findOne(id);
		}
	
}
