package com.ayyub.liga.controller;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import com.ayyub.liga.model.Standing;
import com.ayyub.liga.repository.StandingRepository;

@RestController
@RequestMapping("/api/klasemen")
public class StandingController {
  @Autowired
  StandingRepository standingRepository;

  @GetMapping()
  public ResponseEntity<List<Standing>> getAll() {
    try {
      List<Standing> standings = new ArrayList<Standing>();
      standingRepository.findAll().forEach(standings::add);
      Collections.sort(standings, (curr, next) -> curr.getRank() - next.getRank());
      return new ResponseEntity<>(standings, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}