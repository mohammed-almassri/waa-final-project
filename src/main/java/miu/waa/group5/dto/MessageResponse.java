package miu.waa.group5.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import miu.waa.group5.entity.User;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {
    private Long id;
    private User sender;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long offerId;
    private List<String> imageURLs;
}