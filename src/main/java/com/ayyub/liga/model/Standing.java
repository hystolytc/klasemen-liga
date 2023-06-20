package com.ayyub.liga.model;

import jakarta.persistence.*;

@Entity
@Table(name="standings")
public class Standing {

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private int id;

  @OneToOne
  @JoinColumn(name = "team_id")
  private Team team;

  private String name;

  private String city;

  @Column(name="rank")
  private int rank;

  @Column(name="points")
  private int points;

  @Column(name="win")
  private int win;

  @Column(name="lost")
  private int lost;

  @Column(name="draw")
  private int draw;

  @Column(name="number_of_match")
  private int number_of_match;

  @Column(name="home_goal")
  private int home_goal;

  @Column(name="away_goal")
  private int away_goal;

  public Standing() {}

  public Standing(Team team, int rank, int points, int win, int lost, int draw, int number_of_match, int home_goal, int away_goal) {
    this.team = team;
    this.name = team.getName();
    this.city = team.getCity();
    this.rank = rank;
    this.points = points;
    this.win = win;
    this.lost = lost;
    this.draw = draw;
    this.number_of_match = number_of_match;
    this.home_goal = home_goal;
    this.away_goal = away_goal;
  }

  public String getName() {
    return name;
  }

  public String getCity() {
    return city;
  }

  public int getRank() {
    return rank;
  }

  public int getPoints() {
    return points;
  }

  public int getWin() {
    return win;
  }

  public int getLost() {
    return lost;
  }

  public int getDraw() {
    return draw;
  }

  public int getNumberOfMatch() {
    return number_of_match;
  }

  public int getHomeGoal() {
    return home_goal;
  }

  public int getAwayGoal() {
    return away_goal;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public void setRank(int rank) {
    this.rank = rank;
  }

  public void setPoints(int points) {
    this.points = points;
  }

  public void setWin(int win) {
    this.win = win;
  }
  
  public void setLost(int lost) {
    this.lost = lost;
  }
  
  public void setDraw(int draw) {
    this.draw = draw;
  }

  public void setNumberOfMatch(int number_of_match) {
    this.number_of_match = number_of_match;
  }

  public void setHomeGoal(int home_goal) {
    this.home_goal = home_goal;
  }

  public void setAwayGoal(int away_goal) {
    this.away_goal = away_goal;
  }

  @Override
  public String toString() {
    return String.format("Standing{id=%d, rank=%d, points=%d, win=%d, lost=%d, draw=%d, number_of_match=%d, home_goal=%d, away_goal=%d}", id, rank, points, win, lost, draw, number_of_match, home_goal, away_goal);
  }
}