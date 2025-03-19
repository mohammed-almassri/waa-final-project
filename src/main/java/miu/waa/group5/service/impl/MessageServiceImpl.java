package miu.waa.group5.service.impl;

import lombok.RequiredArgsConstructor;
import miu.waa.group5.dto.MessageRequest;
import miu.waa.group5.dto.MessageResponse;
import miu.waa.group5.dto.UserRequest;
import miu.waa.group5.dto.UserResponse;
import miu.waa.group5.entity.Message;
import miu.waa.group5.entity.User;
import miu.waa.group5.repository.MessageRepository;
import miu.waa.group5.service.MessageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    @Autowired
    private ModelMapper modelMapper;

    public Page<MessageResponse> getMessagesByOffer(Long offerId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("created_at")));
        return messageRepository.findByOfferId(offerId, pageable).map(m->modelMapper.map(m, MessageResponse.class));
    }

    @Override
    public MessageResponse sendMessage(MessageRequest message, UserResponse sender) {
        var m = modelMapper.map(message, Message.class);
        m.setId(null);
        m.setSender(modelMapper.map(sender, User.class));
        var saved = messageRepository.save(m);
        return modelMapper.map(saved, MessageResponse.class);
    }
}
