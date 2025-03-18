package miu.waa.group5.controller;

import lombok.RequiredArgsConstructor;
import miu.waa.group5.dto.OfferRequest;
import miu.waa.group5.dto.OfferResponse;
import miu.waa.group5.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    OfferService offerService;

    public ResponseEntity<OfferResponse> createOffer(@RequestBody OfferRequest offerRequest) {
        OfferResponse offerResponse = offerService.createOffer(offerRequest);
        return ResponseEntity.ok(offerResponse);
    }
}
