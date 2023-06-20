package com.ayyub.liga.repository;

import org.springframework.data.repository.CrudRepository;
import com.ayyub.liga.model.Team;

public interface TeamRepository extends CrudRepository<Team, Integer> {}