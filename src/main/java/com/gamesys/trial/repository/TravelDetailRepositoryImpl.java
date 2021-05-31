package com.gamesys.trial.repository;

import com.gamesys.trial.model.TravelDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Component
@Profile("inMemory")
@RequiredArgsConstructor
public class TravelDetailRepositoryImpl implements TravelDetailRepository {

    private final Map<String, TravelDetail> inMemoryDb;

    @Override
    public Optional<TravelDetail> findByPGI(String pgi) {
        return ofNullable(inMemoryDb.get(pgi));
    }

    @Override
    public void save(TravelDetail travelDetail) {
        inMemoryDb.put(travelDetail.getPgi(), travelDetail);
    }
}
