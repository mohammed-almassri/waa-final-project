package miu.waa.group5.service;

import miu.waa.group5.dto.MessageRequest;
import miu.waa.group5.dto.MessageResponse;
import miu.waa.group5.dto.UserRequest;
import miu.waa.group5.dto.UserResponse;
import miu.waa.group5.entity.Message;
import org.springframework.data.domain.Page;

public interface MessageService {
    public Page<MessageResponse> getMessagesByOffer(Long offerId, int page, int size);
    MessageResponse sendMessage(MessageRequest message, UserResponse senderId);
}
