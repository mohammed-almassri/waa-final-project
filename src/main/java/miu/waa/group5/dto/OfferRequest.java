package miu.waa.group5.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferRequest {

    @NotNull(message = "Property ID is required")
    private Long propertyId;

    @Min(value = 0, message = "Offered price must be positive")
    private int offeredPrice;

    @NotBlank(message = "Message is required")
    private String message;
}