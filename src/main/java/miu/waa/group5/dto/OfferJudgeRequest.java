package miu.waa.group5.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferJudgeRequest {
    @NotNull(message = "isAccepted is required")
    private Boolean isAccepted;
}
