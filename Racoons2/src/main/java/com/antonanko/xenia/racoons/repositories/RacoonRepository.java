package com.antonanko.xenia.racoons.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.antonanko.xenia.racoons.entities.Racoon;

public interface RacoonRepository extends JpaRepository<Racoon, Long> {

}
