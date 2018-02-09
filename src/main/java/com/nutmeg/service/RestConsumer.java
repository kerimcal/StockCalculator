package com.nutmeg.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class RestConsumer {

    public List makeATestRequest(){
        /*final ResponseEntity<IntuitiveHotelSearchResponse[]> response =
                    this.requestForwarder.postRequest(this.hotelConfig.getIntuitiveHotelAvailabilitySearchEndpoint(), request, IntuitiveHotelSearchResponse[].class);
            return Arrays.asList(response.getBody());*/
        return Arrays.asList();
    }

    public List makeAnotherRequest(){
        /*final ResponseEntity<String> responseEntity = requestForwarder.postRequestWithHeaders(hotelConfig.getConfirmUrl(), bookingParts.getBookingRequest(), String.class, headers);
        if(responseEntity.getStatusCode().is2xxSuccessful()){
            LOGGER.info("Hotel confirm response {}", responseEntity.getBody());
            final BookingResponse bookResponse = JacksonConfig.OBJECT_MAPPER.readValue(responseEntity.getBody(), BookingResponse.class);
        }else{
            BookingException ex = new BookingException("TS Hotel returned bad confirm -> " +
                    responseEntity.getStatusCode().value() +
                    responseEntity.getStatusCode().getReasonPhrase());
            return;
        }*/
        return Arrays.asList();
    }
}
