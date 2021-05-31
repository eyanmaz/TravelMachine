package com.gamesys.trial.repository;

import com.gamesys.trial.model.TravelDetail;

import java.util.Optional;

public interface TravelDetailRepository {

    Optional<TravelDetail> findByPGI(String pgi);

    void save(TravelDetail travelDetail);
}
