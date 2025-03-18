package miu.waa.group5.service;

import miu.waa.group5.dto.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface OfferService {

    public OfferResponse createOffer(OfferRequest offerRequest);
    public List<OwnerOffersResponse> findByOwner();
    public OfferJudgeResponse judgeOffer(OfferJudgeRequest offerJudgeRequest, long id);
    public OfferFinalizeResponse finalizeOffer(OfferFinalizeRequest offerFinalizeRequest, long id);
}
