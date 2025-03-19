package miu.waa.group5.service;

import miu.waa.group5.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface OfferService {

    public OfferResponse createOffer(OfferRequest offerRequest);
    public Page<OwnerOffersResponse> findByOwner(Pageable pageable);
    public OfferJudgeResponse judgeOffer(OfferJudgeRequest offerJudgeRequest, long id);
    public OfferFinalizeResponse finalizeOffer(OfferFinalizeRequest offerFinalizeRequest, long id);
    public Page<OfferListResponse> getAllOffers(Pageable pageable);
}
