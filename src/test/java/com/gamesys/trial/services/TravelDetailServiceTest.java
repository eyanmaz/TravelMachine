package com.gamesys.trial.services;

import com.gamesys.trial.exception.DuplicateTravelDetailException;
import com.gamesys.trial.model.TravelDetail;
import com.gamesys.trial.repository.TravelDetailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TravelDetailServiceTest {

    @Mock
    private TravelDetailRepository travelDetailRepository;
    private TravelDetail travelDetail;
    private TravelDetailService travelDetailService;

    @BeforeEach
    void setUp() {
        travelDetailService = new TravelDetailService(travelDetailRepository);
        travelDetail = TravelDetail.builder().date(LocalDate.now()).pgi("abcde123").place("London").build();
    }

    @Test
    void shouldSaveTravelDetailSuccessfully() {
        when(travelDetailRepository.findByPGI(eq("abcde123"))).thenReturn(Optional.empty());

        travelDetailService.saveTravelDetail(travelDetail);

        verify(travelDetailRepository).findByPGI(eq("abcde123"));
        verify(travelDetailRepository).save(eq(travelDetail));
    }

    @Test
    void shouldThrowDuplicateTravelDetailException_when_the_entry_already_exist() {
        when(travelDetailRepository.findByPGI(eq("abcde123"))).thenReturn(Optional.of(travelDetail));

        assertThrows(DuplicateTravelDetailException.class, () -> {
            travelDetailService.saveTravelDetail(travelDetail);
        });
    }

    @Test
    void shouldFindTravelDetailSuccessfully() {
        when(travelDetailRepository.findByPGI(eq("abcde123"))).thenReturn(Optional.of(travelDetail));

        Optional<TravelDetail> actual = travelDetailService.findTravelDetail("abcde123");

        verify(travelDetailRepository).findByPGI(eq("abcde123"));

        assertAll(() -> {
            assertTrue(actual.isPresent());
            assertEquals("abcde123", actual.get().getPgi());
            assertEquals("London", actual.get().getPlace());
        });
    }

    @Test
    void shouldReturnEmpty_when_not_found() {
        when(travelDetailRepository.findByPGI(eq("abcde123"))).thenReturn(Optional.empty());

        Optional<TravelDetail> actual = travelDetailService.findTravelDetail("abcde123");

        verify(travelDetailRepository).findByPGI(eq("abcde123"));

        assertFalse(actual.isPresent());
    }
}