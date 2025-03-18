package miu.waa.group5.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferFinalizeResponse {
    private long id;
    private LocalDateTime soldAt;
}
