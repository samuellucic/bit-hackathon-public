package hr.bithackathon.rental.controller;

import java.util.List;

import hr.bithackathon.rental.domain.dto.CommunityHomeResponse;
import hr.bithackathon.rental.service.CommunityHomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/community-home")
@RequiredArgsConstructor
public class CommunityHomeController {

    private final CommunityHomeService communityHomeService;

    @GetMapping
    public List<CommunityHomeResponse> getCommunityHome() {
        return communityHomeService.getCommunityHomes().stream().map(CommunityHomeResponse::from).toList();
    }

}
