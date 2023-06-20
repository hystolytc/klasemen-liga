package com.ayyub.liga.model;

public class ScoreStats{
  private int home_team_goal;
  private int away_team_goal;
 
  public ScoreStats() {}

  public ScoreStats(int home_team_goal, int away_team_goal) {
    this.home_team_goal = home_team_goal;
    this.away_team_goal = away_team_goal;
  }

  public int getHomeTeamGoal() {
    return home_team_goal;
  }

  public int getAwayTeamGoal() {
    return away_team_goal;
  }

  public void setHomeTeamGoal(int home_team_goal) {
    this.home_team_goal = home_team_goal;
  }

  public void setAwayTeamGoal(int away_team_goal) {
    this.away_team_goal = away_team_goal;
  }
}