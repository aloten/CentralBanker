package com.aidanloten.centralbanker.data.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonRequest {
    private String firstName;
    private String lastName;
    private double salary;
}
