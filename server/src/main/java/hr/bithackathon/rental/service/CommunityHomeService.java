package hr.bithackathon.rental.service;

import hr.bithackathon.rental.domain.CommunityHome;
import hr.bithackathon.rental.domain.Reservation;
import hr.bithackathon.rental.repository.CommunityHomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommunityHomeService {

    private final CommunityHomeRepository communityHomeRepository;

    public Reservation createCommunityHome() {
        // TODO
        return null;
    }

    public CommunityHome getCommunityHome(Long communityHomeId) {
        return communityHomeRepository.findById(communityHomeId).orElseThrow();
    }
}
