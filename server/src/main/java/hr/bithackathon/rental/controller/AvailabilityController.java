package hr.bithackathon.rental.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import hr.bithackathon.rental.domain.TimeRange;
import hr.bithackathon.rental.exception.ErrorCode;
import hr.bithackathon.rental.exception.RentalException;
import hr.bithackathon.rental.service.AvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/action")
@RequiredArgsConstructor
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    @GetMapping(value = "/occupation/community-homes/{communityHomeId}", params = { "from", "to" })
    public List<TimeRange> getOccupation(@PathVariable("communityHomeId") Long communityHomeId,
                                         @RequestParam("from") LocalDate from,
                                         @RequestParam("to") LocalDate to) {
        if (from.isAfter(to)) {
            throw new RentalException(ErrorCode.INVALID_DATE_RANGE);
        }

        var fromTimestamp = from.atStartOfDay(ZoneId.systemDefault()).toInstant();
        var toTimestamp = to.atStartOfDay(ZoneId.systemDefault()).toInstant();

        return availabilityService.findAllOccupiedTimeRanges(communityHomeId, fromTimestamp, toTimestamp);
    }

    @GetMapping(value = "/availability/community-homes/{communityHomeId}", params = { "from", "to" })
    public boolean isAvailable(@PathVariable("communityHomeId") Long communityHomeId,
                               @RequestParam("from") LocalDate from,
                               @RequestParam("to") LocalDate to) {
        if (from.isAfter(to)) {
            throw new RentalException(ErrorCode.INVALID_DATE_RANGE);
        }

        var fromTimestamp = from.atStartOfDay(ZoneId.systemDefault()).toInstant();
        var toTimestamp = to.atStartOfDay(ZoneId.systemDefault()).toInstant();

        return availabilityService.isAvailable(communityHomeId, fromTimestamp, toTimestamp);
    }

}
