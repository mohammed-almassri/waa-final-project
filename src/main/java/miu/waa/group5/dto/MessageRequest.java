package miu.waa.group5.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {

    @NotNull(message = "Offer ID is required")
    private Long offerId;


    @NotBlank(message = "Message content is required")
    private String content;

    @NotEmpty
    private List<String> imageURLs;
}