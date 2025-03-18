package miu.waa.group5.service.impl;

import jakarta.transaction.Transactional;
import miu.waa.group5.dto.*;
import miu.waa.group5.entity.Offer;
import miu.waa.group5.entity.Property;
import miu.waa.group5.entity.StatusType;
import miu.waa.group5.entity.User;
import miu.waa.group5.repository.OfferRepository;
import miu.waa.group5.repository.PropertyRepository;
import miu.waa.group5.repository.UserRepository;
import miu.waa.group5.service.OfferService;
import miu.waa.group5.service.PropertyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private PropertyService propertyService;

    public OfferResponse createOffer(OfferRequest offerRequest) {
        Offer offer = modelMapper.map(offerRequest, Offer.class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("no user with the username"));
        offer.setCustomer(user);

        Property property = propertyRepository.findById(offerRequest.getPropertyId()).orElseThrow(() -> new RuntimeException("no property with the id"));
        offer.setProperty(property);
        if (property.getStatus() == StatusType.SOLD || property.getStatus() == StatusType.CONTINGENT) {
            throw new RuntimeException("sold or contingent property");
        }

        offerRepository.save(offer);
        return modelMapper.map(offer, OfferResponse.class);
    }

    public List<OwnerOffersResponse> findByOwner() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<Offer> offers = offerRepository.findByProperty_Owner_Name(username);
        List<OwnerOffersResponse> ownerOffersResponses = new ArrayList<>();
        offers.forEach(offer -> {
            OwnerOffersResponse ownerOffersResponse = modelMapper.map(offer, OwnerOffersResponse.class);

            ownerOffersResponse.setProperty(PropertyDTO.fromEntity(offer.getProperty()));
            ownerOffersResponses.add(ownerOffersResponse);
        });
        return ownerOffersResponses;

    }

    @Transactional
    public OfferJudgeResponse judgeOffer(OfferJudgeRequest offerJudgeRequest, long id) {
        Offer offer = offerRepository.findById(id).orElseThrow(() -> new RuntimeException("No offer with the id"));
        Property property = propertyRepository.findById(offer.getProperty().getId()).orElseThrow(() -> new RuntimeException("no property with the id"));
        offer.setIsAccepted(offerJudgeRequest.isAccepted());
        offer.setProcessedAt(LocalDateTime.now());
        offerRepository.save(offer);
        if (offerJudgeRequest.isAccepted()) {
            property.setStatus(StatusType.CONTINGENT);
        } else {
            List<Offer> pendingOffers = offerRepository.findPendingOffersByProperty_Id(property.getId());
            StatusType status = pendingOffers.isEmpty() ? StatusType.PENDING : StatusType.AVAILABLE;
            property.setStatus(status);
        }
        propertyRepository.save(property);

        return modelMapper.map(offer, OfferJudgeResponse.class);

    }

    @Transactional
    public OfferFinalizeResponse finalizeOffer(OfferFinalizeRequest offerFinalizeRequest, long id) {
        Offer offer = offerRepository.findById(id).orElseThrow(() -> new RuntimeException("No offer with the id"));
        Property property = propertyRepository.findById(offer.getProperty().getId()).orElseThrow(() -> new RuntimeException("no property with the id"));
        offer.setSoldAt(LocalDateTime.now());
        property.setStatus(StatusType.SOLD);
        List<Offer> offers = offerRepository.findAllByIdNotAndProperty_Id(offer.getId(), property.getId());
        offers.forEach(o -> {
            o.setIsAccepted(false);
            o.setProcessedAt(LocalDateTime.now());
        });
        return modelMapper.map(offer, OfferFinalizeResponse.class);

    }

    @Transactional
    @Override
    public List<OfferListResponse> getAllOffers() {
        return offerRepository.findAll().stream()
                .map(this::mapToOfferListResponse)
                .collect(Collectors.toList());
    }

    private OfferListResponse mapToOfferListResponse(Offer offer) {
        Property property = offer.getProperty();
        PropertySummaryResponse propertyResponse = new PropertySummaryResponse(
                property.getId(), property.getTitle(), property.getPrice());

        return new OfferListResponse(
                offer.getId(),
                propertyResponse,
                offer.getMessage(),
                offer.getOfferedPrice(),
                offer.getIsAccepted()
        );
    }


}
