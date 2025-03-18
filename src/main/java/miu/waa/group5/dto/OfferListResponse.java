package miu.waa.group5.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OfferListResponse {
    private Long id;
    private PropertySummaryResponse property;
    private String message;
    private double offeredPrice;
    private Boolean isAccepted;
}
