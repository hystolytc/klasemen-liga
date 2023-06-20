package com.ayyub.liga.repository;

import java.util.*;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ayyub.liga.model.Statistic;

public interface StatisticRepository extends JpaRepository<Statistic, Integer> {
  List<Statistic> findBySchedule(LocalDate schedule);
}