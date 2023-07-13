package com.aidanloten.centralbanker.data.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "GameState")
public class GameState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Builder.Default
    private boolean isGamePaused = false;
    private LocalDate date;
    @Builder.Default
    private boolean shouldStreamPersonAssets = false;
    private int balanceSheetIdOfPersonInModal;
}
