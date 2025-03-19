package miu.waa.group5.controller;

import lombok.RequiredArgsConstructor;
import miu.waa.group5.dto.MessageRequest;
import miu.waa.group5.dto.MessageResponse;
import miu.waa.group5.entity.Message;
import miu.waa.group5.entity.User;
import miu.waa.group5.service.MessageService;
import miu.waa.group5.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;
    private final UserService userService;

    @MessageMapping("/chat.sendMessage/{offerId}")
    @SendTo("/topic/offer/{offerId}")
    public MessageResponse sendMessage(MessageRequest messageRequest, Principal principal) {
        var user = userService.findByName(principal.getName());
        var savedMessage = messageService.sendMessage(messageRequest,user);
        return savedMessage;
    }
}
