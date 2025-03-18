package miu.waa.group5.service;

import miu.waa.group5.dto.OfferRequest;
import miu.waa.group5.dto.OfferResponse;
import org.springframework.web.bind.annotation.RequestBody;

public interface OfferService {

    public OfferResponse createOffer(OfferRequest offerRequest);
}
