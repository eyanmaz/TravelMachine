package com.gamesys.trial.controllers;

import com.gamesys.trial.model.TravelDetail;
import com.gamesys.trial.model.errors.ErrorResponse;
import com.gamesys.trial.services.TravelDetailService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
public class TravelDetailController {
    private TravelDetailService travelDetailService;

    public TravelDetailController(TravelDetailService travelDetailService) {
        this.travelDetailService = travelDetailService;
    }

    @ApiOperation(value = "Create a new Travel Detail")
    @ApiResponses(value =
            {
                    @ApiResponse(code = 201, message = "Created", response = ResponseEntity.class),
                    @ApiResponse(code = 400, message = "List of errors.", response = ErrorResponse.class),
                    @ApiResponse(code = 409, message = "Duplicate Travel Details", response = ErrorResponse.class),
                    @ApiResponse(code = 500, message = "Internal Server Error", response = ResponseEntity.class)})
    @PostMapping(value = "/v1/travel-details", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity addTravelDetail(@RequestBody @Valid TravelDetail travelDetail) {
        travelDetailService.saveTravelDetail(travelDetail);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{pgi}")
                .buildAndExpand(travelDetail.getPgi())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @ApiOperation(value = "Return the travel detail by Pgi")
    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "Success", response = TravelDetail.class),
                    @ApiResponse(code = 404, message = "Not Found Travel Detail", response = ResponseEntity.class),
                    @ApiResponse(code = 500, message = "Internal Server Error", response = ResponseEntity.class)})
    @GetMapping(value = "/v1/travel-details/{pgi}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity getTravelDetail(@PathVariable("pgi") String pgi) {
        return travelDetailService.findTravelDetail(pgi)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
