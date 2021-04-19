package com.akvone.data_jpa.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class StreetEntity {

  @Id
  @GeneratedValue
  Long id;

  String name;

}
