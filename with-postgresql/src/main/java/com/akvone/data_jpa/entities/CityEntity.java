package com.akvone.data_jpa.entities;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CityEntity {

  @Id
  @GeneratedValue
  Long id;

  String name;

  @OneToMany(cascade = {CascadeType.ALL})
  List<StreetEntity> streets;
}
