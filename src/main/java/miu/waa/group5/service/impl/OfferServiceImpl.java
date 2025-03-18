package miu.waa.group5.service.impl;

import miu.waa.group5.dto.OfferRequest;
import miu.waa.group5.dto.OfferResponse;
import miu.waa.group5.entity.Offer;
import miu.waa.group5.entity.Property;
import miu.waa.group5.entity.User;
import miu.waa.group5.repository.OfferRepository;
import miu.waa.group5.repository.PropertyRepository;
import miu.waa.group5.repository.UserRepository;
import miu.waa.group5.service.OfferService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class OfferServiceImpl implements OfferService {

    @Autowired
    OfferRepository offerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    public OfferResponse createOffer(OfferRequest offerRequest) {
        Offer offer = modelMapper.map(offerRequest, Offer.class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("no user with the username"));
        offer.setCustomer(user);

        Property property = propertyRepository.findById(offerRequest.getPropertyId()).orElseThrow(() -> new RuntimeException("no property with the id"));
        offer.setProperty(property);

        offerRepository.save(offer);
        return modelMapper.map(offer, OfferResponse.class);
    }


}
