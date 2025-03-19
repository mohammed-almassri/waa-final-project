package miu.waa.group5.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PropertySummaryResponse {
    private Long id;
    private String title;
    private double price;
}
