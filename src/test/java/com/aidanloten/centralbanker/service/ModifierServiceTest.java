package com.aidanloten.centralbanker.service;

import com.aidanloten.centralbanker.data.entities.descriptors.person.Personality;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ModifierServiceTest {

    @InjectMocks
    private ModifierService modifierService;

    @Mock
    private PersonService personService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void calculateProductionModifierValue() {
        Personality personality = new Personality();
        personality.setHardworking(10);
        personality.setInnovative(10);

        // Mock the isRandomNumberLessThanOrEqual method using partial mocking
        ModifierService spyModifierService = org.mockito.Mockito.spy(modifierService);

        double modifierValue = 0;
        for (int i = 0; i < 100; i++) {
            modifierValue += spyModifierService.calculateProductionModifierValue(personality);
        }
        // value should be close to 20
        System.out.println(modifierValue / 100);
    }

}
