package com.antonanko.xenia.racoons.entities;

import javax.persistence.Entity;

@Entity
public class Racoon extends BaseEntity {
	
    private String name;
    
    private int weight;

	public Racoon(String name, int weight) {
		super();
		this.name = name;
		this.weight = weight;
	}
	
	public Racoon() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	

    
}
