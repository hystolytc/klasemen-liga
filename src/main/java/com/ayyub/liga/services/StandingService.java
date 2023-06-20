package com.ayyub.liga.services;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.ayyub.liga.model.Standing;
import com.ayyub.liga.repository.StandingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StandingService {
  StandingRepository standingRepository;
  Logger logger = LoggerFactory.getLogger(StandingService.class);

  public StandingService(StandingRepository standingRepository) {
    this.standingRepository = standingRepository;
  }

  private void swap(List<Standing> standings, int currIndex) {
    Standing tmpStanding = standings.get(currIndex-1);
    standings.set(currIndex-1, standings.get(currIndex));
    standings.set(currIndex, tmpStanding);
  }

  public void reorderStanding() {
    List<Standing> standings = new ArrayList<Standing>();
    standingRepository.findAll().forEach(standings::add);
    
    for (int i=0; i<standings.size(); i++) {
      for (int j=1; j<=standings.size()-1; j++) {
        int points = standings.get(j-1).getPoints() - standings.get(j).getPoints();
        logger.info("SORTING {} {} {}", points, standings.get(j-1).getName(),  standings.get(j).getName());
        if (points < 0) {
          swap(standings, j);
          break;
        }
        
        int homeAwayGoal = (standings.get(j).getHomeGoal() + standings.get(j).getAwayGoal()) - (standings.get(j-1).getHomeGoal() + standings.get(j-1).getAwayGoal());
        if (points == 0 && homeAwayGoal > 0) {
          swap(standings, j);
          break;
        }

        int awayGoal = standings.get(j).getAwayGoal() - standings.get(j-1).getAwayGoal();
        if (homeAwayGoal == 0 && awayGoal > 0) {
          swap(standings, j);
          break;
        }
      }
    }

    List<Standing> updatedStandings = new ArrayList<Standing>();

    for (int i=0; i<standings.size(); i++) {
      standings.get(i).setRank(i + 1);
      updatedStandings.add(standings.get(i));
    }

    standingRepository.saveAll(updatedStandings);
  }
}