package hinc.come.guiltyornot.api.store.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "detective_images")
public class DetectiveImagesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "detective_id", referencedColumnName = "id")
    DetectiveEntity detective;

    @Column(name = "detective_id", updatable = false, insertable = false)
    Long detectiveId;

    String imgUrl;
}
