package miu.waa.group5.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerOffersResponse {

    private Long id;
    private String message;
    private Double offeredPrice;
    private Boolean isAccepted;
    private PropertyDTO property;
    private UserResponse customer;
}
