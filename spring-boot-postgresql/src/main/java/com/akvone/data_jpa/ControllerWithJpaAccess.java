package com.akvone.data_jpa;

import com.akvone.data_jpa.entities.CityEntity;
import com.akvone.data_jpa.entities.StreetEntity;
import com.akvone.data_jpa.repositories.CityRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("jpa")
public class ControllerWithJpaAccess {

  private final CityRepository cityRepository;

  @GetMapping("/cities/{id}")
  public ResponseEntity<CityEntity> getCity(@PathVariable long id) {
    CityEntity city = cityRepository.getOne(id);
    List<StreetEntity> streets = city.getStreets();

    for (StreetEntity street : streets) {
      log.info("Found street [city={}, streets={}]", city.getName(), street.getName());
    }

    return new ResponseEntity<>(city, HttpStatus.OK);
  }
}
