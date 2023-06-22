package com.aidanloten.centralbanker.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SseEvent {
    private String eventName;
    private Object data;
}
