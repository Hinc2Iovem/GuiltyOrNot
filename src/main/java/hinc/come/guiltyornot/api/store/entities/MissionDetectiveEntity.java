package hinc.come.guiltyornot.api.store.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "mission_detective")
public class MissionDetectiveEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Boolean withVictim;

    @Column(unique = true)
    String title;

    @Column(length = 2500)
    String description;

    @Builder.Default
    Integer levelOfDifficulty = 1;

    Integer rewardExp;

    Integer rewardMoney;

    Integer defeatExp;

    Integer defeatMoney;

    String imgUrl;

    String role;

    @OneToOne
    @JoinColumn(name = "detective_id", referencedColumnName = "id")
    DetectiveEntity detective;

    @Column(name = "detective_id", updatable = false, insertable = false)
    Long detectiveId;

    @OneToMany
    @JoinColumn(name = "character_entity_id", referencedColumnName = "id")
    List<CharacterEntity> characters;

    @Transient
    List<Long> characterIds;

    @OneToOne
    @JoinColumn(name = "character_victim_id", referencedColumnName = "id")
    CharacterVictimEntity characterVictim;

    @Column(name = "character_victim_id", updatable = false, insertable = false)
    Long characterVictimId;

    @OneToOne
    @JoinColumn(name = "character_guilty_id", referencedColumnName = "id")
    CharacterGuiltyEntity characterGuilty;

    @Column(name = "character_guilty_id", updatable = false, insertable = false)
    Long characterGuiltyId;
}
