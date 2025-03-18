package miu.waa.group5.controller;

import lombok.RequiredArgsConstructor;
import miu.waa.group5.dto.MessageResponse;
import miu.waa.group5.entity.Message;
import miu.waa.group5.service.MessageService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @GetMapping("/api/offer/{offerId}/messages")
    public Page<MessageResponse> getMessagesByOffer(
            @PathVariable Long offerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<MessageResponse> messagePage = messageService.getMessagesByOffer(offerId, page, size);
        return messagePage;
    }
}
