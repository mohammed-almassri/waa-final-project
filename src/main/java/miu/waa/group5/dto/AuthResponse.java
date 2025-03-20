package miu.waa.group5.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String token;
    private long id;
    private String email;
    private String name;
    private String imageURL;
    private boolean isActive;
    private boolean approved;
}
