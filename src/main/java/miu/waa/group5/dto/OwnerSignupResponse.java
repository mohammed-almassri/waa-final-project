package miu.waa.group5.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerSignupResponse {

    private Long id;
    private String name;
    private String email;
    private String imageURL;
    private String token;
}
