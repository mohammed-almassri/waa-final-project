package miu.waa.group5.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferJudgeResponse {
    private long id;
    private boolean isAccepted;
}
