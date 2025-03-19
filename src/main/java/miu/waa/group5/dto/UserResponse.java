package miu.waa.group5.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private String imageUrl;
    private boolean isActive;
    private boolean approved;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
