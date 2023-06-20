package com.ayyub.liga.model;

import jakarta.persistence.*;

@Entity
@Table(name="teams")
public class Team {

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private int id;

  @Column(name="name")
  private String name;

  @Column(name="city")
  private String city;

  public Team() {}

  public Team(String name, String city) {
    this.name = name;
    this.city = city;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  @Override
  public String toString() {
    return String.format("Team{id=%d, name=%s, city=%s}", id, name, city);
  }
}