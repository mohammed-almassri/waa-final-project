package miu.waa.group5.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerOffersResponse {

    private Long id;
    private String message;
    private Double offeredPrice;
    private Boolean isAccepted;
    private LocalDateTime soldAt;
    private PropertyDTO property;
    private UserResponse customer;
}
