package com.antonenko.xenia.racoons.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.antonenko.xenia.racoons.entities.Racoon;

public interface RacoonRepository extends JpaRepository<Racoon, Long> {

}
