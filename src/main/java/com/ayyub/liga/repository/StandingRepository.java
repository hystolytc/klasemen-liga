package com.ayyub.liga.repository;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ayyub.liga.model.Standing;

public interface StandingRepository extends JpaRepository<Standing, Integer> {
  Optional<Standing> findByName(String name);
}