package miu.waa.group5.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "media_details")
public class MediaDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer width;
    private Integer height;
    private Duration duration;

    @OneToOne
    @JoinColumn(name = "media_id")
    private Media media;
}
