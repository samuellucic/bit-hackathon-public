package hr.bithackathon.rental.service;

import java.util.List;

import hr.bithackathon.rental.domain.CommunityHome;
import hr.bithackathon.rental.domain.Reservation;
import hr.bithackathon.rental.domain.TimeRange;
import hr.bithackathon.rental.repository.CommunityHomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommunityHomeService {

    private final CommunityHomeRepository communityHomeRepository;

    public List<CommunityHome> getCommunityHomes() {
        return communityHomeRepository.findAll();
    }

    public CommunityHome getCommunityHome(Long communityHomeId) {
        return communityHomeRepository.findById(communityHomeId).orElseThrow();
    }

}
