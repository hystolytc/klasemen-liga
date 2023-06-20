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
import com.ayyub.liga.model.Standing;
import com.ayyub.liga.model.Statistic;
import com.ayyub.liga.model.ScoreStats;
import com.ayyub.liga.model.Team;
import com.ayyub.liga.repository.StandingRepository;
import com.ayyub.liga.repository.StatisticRepository;
import com.ayyub.liga.repository.TeamRepository;
import com.ayyub.liga.services.StandingService;

@RestController
@RequestMapping("/api/statistik")
public class StatisticController {

  @Autowired
  StatisticRepository statisticRepository;

  @Autowired
  StandingRepository standingRepository;

  @Autowired
  TeamRepository teamRepository;

  @GetMapping()
  public ResponseEntity<List<Statistic>> getAll() {
    try {
      List<Statistic> statistics = new ArrayList<Statistic>();
      statisticRepository.findAll().forEach(stats -> {
        stats.setStatistics(new ScoreStats(stats.getHomeTeamGoal(), stats.getAwayTeamGoal()));
        statistics.add(stats);
      });
      return new ResponseEntity<>(statistics, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping()
  public ResponseEntity add(@RequestBody Statistic stats) {
    try {
      int homeTeamId = stats.getHomeTeam();
      int awayTeamId = stats.getAwayTeam();

      if ((homeTeamId == awayTeamId) || (homeTeamId == 0 || awayTeamId == 0)) {
        return new ResponseEntity<>("id home_team atau away_team tidak valid", HttpStatus.BAD_REQUEST);
      }
      
      Optional<Team> teamHome = teamRepository.findById(homeTeamId);
      Optional<Team> teamAway = teamRepository.findById(awayTeamId);

      if (!teamHome.isPresent() || !teamAway.isPresent()) return new ResponseEntity<>("id home_team atau away_team tidak ditemukan", HttpStatus.BAD_REQUEST);

      if (stats.getSchedule() == null) return new ResponseEntity<>("schedule wajib di isi", HttpStatus.BAD_REQUEST);
      
      List<Statistic> statistics = statisticRepository.findBySchedule(stats.getSchedule());
      if (!statistics.isEmpty()) {
        boolean isMultipleSchedule = false;
        for (int i=0; i<statistics.size(); i++) {
          if (statistics.get(i).getHomeTeam() == stats.getHomeTeam() || statistics.get(i).getAwayTeam() == stats.getAwayTeam()) {
            isMultipleSchedule = true;
            break;
          }
        }

        if (isMultipleSchedule)
          return new ResponseEntity<>("home_team atau away_team memilki schedule yang lain pada tanggal ini", HttpStatus.BAD_REQUEST);
      }

      Statistic _stats = statisticRepository.save(
        new Statistic(
          stats.getHomeTeam(),
          stats.getAwayTeam(),
          stats.getSchedule(),
          stats.getStatistics().getHomeTeamGoal(),
          stats.getStatistics().getAwayTeamGoal()
        )
      );

      Optional<Standing> standingTeamHome = standingRepository.findByName(teamHome.get().getName());
      Optional<Standing> standingTeamAway = standingRepository.findByName(teamAway.get().getName());
      Standing standTeamHome = standingTeamHome.get();
      Standing standTeamAway = standingTeamAway.get();
      
      if (stats.getStatistics().getHomeTeamGoal() > stats.getStatistics().getAwayTeamGoal()) {
        standTeamHome.setPoints(standTeamHome.getPoints() + 3);
        standTeamHome.setWin(standTeamHome.getWin() + 1);
        standTeamAway.setLost(standTeamAway.getLost() + 1);      
      } else if (stats.getStatistics().getHomeTeamGoal() == stats.getStatistics().getAwayTeamGoal()) {
        standTeamHome.setPoints(standTeamHome.getPoints() + 1);
        standTeamHome.setDraw(standTeamHome.getDraw() + 1);
        standTeamAway.setPoints(standTeamAway.getPoints() + 1);
        standTeamAway.setDraw(standTeamAway.getDraw() + 1);
      } else {
        standTeamAway.setPoints(standTeamAway.getPoints() + 3);
        standTeamAway.setWin(standTeamAway.getWin() + 1);
        standTeamHome.setLost(standTeamHome.getLost() + 1);
      }

      standTeamHome.setHomeGoal(standTeamHome.getHomeGoal() + stats.getStatistics().getHomeTeamGoal());
      standTeamHome.setNumberOfMatch(standTeamHome.getNumberOfMatch() + 1);
      standTeamAway.setAwayGoal(standTeamAway.getAwayGoal() + stats.getStatistics().getAwayTeamGoal());
      standTeamAway.setNumberOfMatch(standTeamAway.getNumberOfMatch() + 1);

      List<Standing> standings = new ArrayList<Standing>();
      standings.add(standTeamHome);
      standings.add(standTeamAway);

      standingRepository.saveAll(standings);

      StandingService standingService = new StandingService(standingRepository);
      standingService.reorderStanding();
      
      return new ResponseEntity<>(_stats, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}