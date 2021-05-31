package com.gamesys.trial.controllers;

import com.gamesys.trial.exception.DuplicateTravelDetailException;
import com.gamesys.trial.model.TravelDetail;
import com.gamesys.trial.services.TravelDetailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TravelDetailControllerTest {

    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private TravelDetailService travelDetailService;
    private TravelDetail travelDetail;

    @BeforeEach
    void setUp() {
        travelDetail = TravelDetail.builder()
                .pgi("abcde123")
                .place("Tokyo")
                .date(LocalDate.now())
                .build();
    }

    @Test
    void shouldAddNewTravelDetailSuccessfully() {
        webTestClient
                .post()
                .uri("/v1/travel-details")
                .body(Mono.just(travelDetail), TravelDetail.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void shouldReturn_HttpConflict_when_DuplicateTravelDetailException_thrown() {
        doThrow(new DuplicateTravelDetailException("foo")).when(travelDetailService).saveTravelDetail(any(TravelDetail.class));

        webTestClient
                .post()
                .uri("/v1/travel-details")
                .body(Mono.just(travelDetail), TravelDetail.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void shouldReturn_Bad_Request_when_Validation_Error() {
        travelDetail.setPgi("1234asdef");

        webTestClient
                .post()
                .uri("/v1/travel-details")
                .body(Mono.just(travelDetail), TravelDetail.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.BAD_REQUEST)
                .expectBody()
                .jsonPath("$.error.code")
                .isEqualTo("ERROR-1");
    }

    @Test
    void shouldReturnTravelDetailSuccessfully() {
        when(travelDetailService.findTravelDetail(anyString())).thenReturn(Optional.of(travelDetail));

        webTestClient
                .get()
                .uri("/v1/travel-details/abcde123")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .jsonPath("$.pgi")
                .isEqualTo("abcde123");
    }

    @Test
    void shouldReturn_NOT_FOUND_when_TravelDetail_is_not_in_Db() {
        when(travelDetailService.findTravelDetail(anyString())).thenReturn(Optional.empty());

        webTestClient
                .get()
                .uri("/v1/travel-details/abcde123")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isNotFound();
    }
}