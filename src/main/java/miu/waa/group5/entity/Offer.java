package miu.waa.group5.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "propertyId", nullable = false)
    private Property property;

    @ManyToOne
    @JoinColumn(name = "customerId", nullable = false)
    private User customer;

    @Column(nullable = false)
    private Double offeredPrice;

    private Boolean isAccepted;

    private LocalDateTime processedAt;

    private LocalDateTime soldAt;

    @Column(columnDefinition = "TEXT")
    private String message;
}
