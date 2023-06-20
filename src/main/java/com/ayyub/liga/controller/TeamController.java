package com.ayyub.liga.controller;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import com.ayyub.liga.model.Team;
import com.ayyub.liga.model.Standing;
import com.ayyub.liga.repository.TeamRepository;
import com.ayyub.liga.repository.StandingRepository;

@RestController
@RequestMapping("/api/tim")
public class TeamController {
  
  @Autowired
  TeamRepository teamRepository;

  @Autowired
  StandingRepository standingRepository;

  @GetMapping()
  public ResponseEntity<List<Team>> getAllTeam() {
    try {
      List<Team> teams = new ArrayList<Team>();
      teamRepository.findAll().forEach(teams::add);
      return new ResponseEntity<>(teams, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<Team> getTeamById(@PathVariable int id) {
    try {
      Optional<Team> team = teamRepository.findById(id);
      if (!team.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      return new ResponseEntity<>(team.get(), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping()
  public ResponseEntity<Team> addTeam(@RequestBody Team team) {
    try {
      Team _team = teamRepository.save(new Team(team.getName(), team.getCity()));
      standingRepository.save(new Standing(_team, 0, 0, 0, 0, 0, 0, 0, 0));
      return new ResponseEntity<>(_team, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("{id}")
  public ResponseEntity<Team> updateTeam(@PathVariable("id") int id, @RequestBody Team team) {
    try {
      Optional<Team> data = teamRepository.findById(id);
      if (!data.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      Team _team = data.get();
      _team.setName(team.getName());
      _team.setCity(team.getCity());
      return new ResponseEntity<>(teamRepository.save(_team), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("{id}")
  public ResponseEntity<HttpStatus> removeTeam(@PathVariable int id) {
    try {
      teamRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}