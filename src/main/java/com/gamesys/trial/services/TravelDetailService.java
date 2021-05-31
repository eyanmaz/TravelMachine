package com.gamesys.trial.services;

import com.gamesys.trial.exception.DuplicateTravelDetailException;
import com.gamesys.trial.model.TravelDetail;
import com.gamesys.trial.repository.TravelDetailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class TravelDetailService {
    private TravelDetailRepository travelDetailRepository;

    public TravelDetailService(TravelDetailRepository travelDetailRepository) {
        this.travelDetailRepository = travelDetailRepository;
    }

    public void saveTravelDetail(TravelDetail travelDetail){
        Optional<TravelDetail> optUserTravelDetail = travelDetailRepository.findByPGI(travelDetail.getPgi());
        if (optUserTravelDetail.isPresent()){
            TravelDetail utd = optUserTravelDetail.get();
            if (utd.getPlace().equals(travelDetail.getPlace()) && utd.getDate().equals(travelDetail.getDate())){
                log.error("User travel detail for same place and date is already exist, travel detail:{}", travelDetail);
                throw new DuplicateTravelDetailException("User travel detail is already exist!");
            }
        }
        travelDetailRepository.save(travelDetail);
    }

    public Optional<TravelDetail> findTravelDetail(String pgi) {
        return travelDetailRepository.findByPGI(pgi);
    }
}
