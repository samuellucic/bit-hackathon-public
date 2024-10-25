package hr.bithackathon.rental.service;

import hr.bithackathon.rental.domain.CommunityHomePlan;
import hr.bithackathon.rental.exception.ErrorCode;
import hr.bithackathon.rental.exception.RentalException;
import hr.bithackathon.rental.repository.CommunityHomePlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommunityHomePlanService {

    private final CommunityHomePlanRepository communityHomePlanRepository;

    public CommunityHomePlan getCommunityHomePlan(Long communityHomePlanId) {
        return communityHomePlanRepository.findById(communityHomePlanId).orElseThrow(() -> new RentalException(ErrorCode.COMMUNITY_HOME_NOT_FOUND));
    }

    public CommunityHomePlan getLatestCommunityHomePlan(Long communityHomeId) {
        return communityHomePlanRepository.findByCommunityHomeIdOrderByCreatedAtDesc(communityHomeId)
                                          .orElseThrow(() -> new RentalException(ErrorCode.COMMUNITY_HOME_PLAN_NOT_FOUND));
    }

}
