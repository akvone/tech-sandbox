package com.akvone.dev;

import com.akvone.data_jpa.entities.CityEntity;
import com.akvone.data_jpa.entities.StreetEntity;
import com.akvone.data_jpa.repositories.CityRepository;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
@RequiredArgsConstructor
public class DevStartupInitializer {

  private final CityRepository cityRepository;

  @EventListener(ApplicationReadyEvent.class)
  public void onApplicationReadyEvent() {
    CityEntity cityEntity = new CityEntity();
    cityEntity.setName("Moscow");
    StreetEntity streetEntity = new StreetEntity();
    streetEntity.setName("Lenina");
    cityEntity.setStreets(Collections.singletonList(streetEntity));

    cityRepository.save(cityEntity);
  }
}
