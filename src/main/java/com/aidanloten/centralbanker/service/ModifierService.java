package com.aidanloten.centralbanker.service;

import com.aidanloten.centralbanker.data.dao.modifier.ModifierRepository;
import com.aidanloten.centralbanker.data.entities.descriptors.economy.Modifier;
import com.aidanloten.centralbanker.data.entities.descriptors.person.Personality;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ModifierService {
    private final ModifierRepository modifierRepository;
    private final PersonService personService;
    Logger logger = LoggerFactory.getLogger(ModifierService.class);

    public ModifierService(ModifierRepository modifierRepository, PersonService personService) {
        this.modifierRepository = modifierRepository;
        this.personService = personService;
    }

    public void saveModifier(Modifier modifier) {
        modifierRepository.save(modifier);
    }

    private boolean isRandomNumberLessThanOrEqual(double threshold) {
        return Math.random() <= threshold;
    }

    /**
     * Change production modifier value by 100 if random number <= algorithm value based on personality
     * Algorithm returns a number out of 100 that represents chance of increasing modifier.
     */
    public double calculateProductionModifierValue(Personality personality) {
        double chanceOfIncrease = personality.getHardworking() / 100D + personality.getInnovative() / 100D;
        if (isRandomNumberLessThanOrEqual(chanceOfIncrease)) {
            logger.info(
                    String.format("Personality id: %d; production modifier should be increased", personality.getId()));
            return 100D;
        }
        return 0;
    }

}
