package com.ayyub.liga.model;

import java.time.LocalDate;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="statistics")
public class Statistic {

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private int id;

  @Column(name="home_team")
  private int home_team;

  @Column(name="away_team")
  private int away_team;

  @Column(name="schedule")
  private LocalDate schedule;

  @Column(name="home_team_goal")
  private int home_team_goal;

  @Column(name="away_team_goal")
  private int away_team_goal;

  @Transient
  private ScoreStats statistics;

  public Statistic() {}

  public Statistic(int home_team, int away_team, LocalDate schedule, int home_team_goal, int away_team_goal) {
    this.home_team = home_team;
    this.away_team = away_team;
    this.schedule = schedule;
    this.home_team_goal = home_team_goal;
    this.away_team_goal = away_team_goal;
    this.statistics = new ScoreStats(home_team_goal, away_team_goal);
  }
  
  @JsonIgnore
  public int getId() {
    return id;
  }

  public int getHomeTeam() {
    return home_team;
  }

  public int getAwayTeam() {
    return away_team;
  }

  public LocalDate getSchedule() {
    return schedule;
  }

  @JsonIgnore
  public int getHomeTeamGoal() {
    return home_team_goal;
  }

  @JsonIgnore
  public int getAwayTeamGoal() {
    return away_team_goal;
  }

  public ScoreStats getStatistics() {
    return statistics;
  }

  public void setHomeTeam(int home_team) {
    this.home_team = home_team;
  }

  public void setAwayTeam(int away_team) {
    this.away_team = away_team;
  }

  public void setSchedule(LocalDate schedule) {
    this.schedule = schedule;
  }

  public void setStatistics(ScoreStats statistics) {
    this.statistics = statistics;
  }

  @Override
  public String toString() {
    return String.format("Statistic{id=%d, home_team=%s, away_team=%s, schedule=%t}", id, home_team, away_team, schedule);
  }
}
