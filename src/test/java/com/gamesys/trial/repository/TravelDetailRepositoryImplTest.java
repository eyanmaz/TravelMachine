package com.gamesys.trial.repository;

import com.gamesys.trial.model.TravelDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TravelDetailRepositoryImplTest {

    private TravelDetailRepositoryImpl travelDetailRepository;
    private Map<String, TravelDetail> inMemoryDB;

    @BeforeEach
    void setUp() {
        inMemoryDB = new HashMap<>();
        travelDetailRepository = new TravelDetailRepositoryImpl(inMemoryDB);
    }

    @Test
    void shouldFindExistingTravelDetail() {
        inMemoryDB.put("abcde12", TravelDetail.builder().date(LocalDate.of(2021, 5, 31)).pgi("abcde12").place("London").build());

        Optional<TravelDetail> actual = travelDetailRepository.findByPGI("abcde12");

        assertAll(() -> {
            assertTrue(actual.isPresent());
            assertEquals("London", actual.get().getPlace());
            assertEquals("abcde12", actual.get().getPgi());
            assertEquals(LocalDate.of(2021, 5, 31), actual.get().getDate());
        });
    }

    @Test
    void shouldSaveTravelDetailIntoDBSuccesfully() {

        TravelDetail travelDetail = TravelDetail.builder().date(LocalDate.of(2021, 5, 31)).pgi("abcde12").place("London").build();

        travelDetailRepository.save(travelDetail);

        assertAll(() -> {
            assertEquals(1, inMemoryDB.size());
            assertNotNull(inMemoryDB.get(travelDetail.getPgi()));
        });
    }
}