package hr.bithackathon.rental.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import hr.bithackathon.rental.domain.TimeRange;
import hr.bithackathon.rental.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/availability")
@RequiredArgsConstructor
public class AvailabilityController {

    private final ContractService contractService;

    @GetMapping(value = "/{communityHomeId}", params = { "from", "to" })
    public List<TimeRange> getAvailability(@PathVariable("communityHomeId") Long communityHomeId,
                                           @RequestParam("from") LocalDate from,
                                           @RequestParam("to") LocalDate to) {
        var fromTimestamp = from.atStartOfDay(ZoneId.systemDefault()).toInstant();
        var toTimestamp = to.atStartOfDay(ZoneId.systemDefault()).toInstant();

        return contractService.findAllOccupiedTimeRanges(communityHomeId, fromTimestamp, toTimestamp);
    }

}
