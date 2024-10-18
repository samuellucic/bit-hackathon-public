package hr.bithackathon.rental.service;

import hr.bithackathon.rental.domain.CommunityHomePlan;
import hr.bithackathon.rental.domain.Reservation;
import hr.bithackathon.rental.repository.CommunityHomePlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommunityHomePlanService {

    private final CommunityHomePlanRepository communityHomePlanRepository;

    public Reservation createCommunityHomePlan() {
        // TODO nije jos moj posao
        return null;
    }

    public CommunityHomePlan getCommunityHomePlan(Long communityHomePlanId) {
        return communityHomePlanRepository.findById(communityHomePlanId).orElseThrow();
    }
}
