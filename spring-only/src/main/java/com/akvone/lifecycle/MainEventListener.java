package com.akvone.lifecycle;

import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MainEventListener {

  @EventListener(ContextClosedEvent.class)
  public void onContextClosedEvent(ContextClosedEvent event){
    System.out.println(event);
  }
}
