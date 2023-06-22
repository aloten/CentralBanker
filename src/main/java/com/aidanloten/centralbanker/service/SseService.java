package com.aidanloten.centralbanker.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * Server Sent Event Service
 */

@Service
public class SseService {
    private final ApplicationEventPublisher eventPublisher;
    private final PersonService personService;

    public SseService(ApplicationEventPublisher eventPublisher, PersonService personService) {
        this.eventPublisher = eventPublisher;
        this.personService = personService;
    }

    public void syncAssetsOfPersonInModal() {
        dispatchEventToClient(EventName.ASSETS.name(), "need to figure out how to handle not having data prop here");
    }

    public void syncPeople() {
        dispatchEventToClient(EventName.PEOPLE.name(), "findAllPeople() stand in");
    }

    private void dispatchEventToClient(String eventName, Object data) {
        SseEvent event = new SseEvent(eventName, data);
        eventPublisher.publishEvent(event);
    }
}
