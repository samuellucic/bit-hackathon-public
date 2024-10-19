package hr.bithackathon.rental.service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import hr.bithackathon.rental.domain.Contract;
import hr.bithackathon.rental.domain.TimeRange;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvailabilityService {

    private final ContractService contractService;

    public List<TimeRange> findAllOccupiedTimeRanges(Long communityHomeId, Instant start, Instant end) {
        var contracts = contractService.findAllBetweenStartAndEnd(communityHomeId, start, end);

        // TODO: If it overlaps, connect it
        return contracts.stream()
                        .map(Contract::getReservation)
                        .map(reservation -> new TimeRange(reservation.getDatetimeFrom().minus(Duration.ofHours(1)),
                                                          reservation.getDatetimeTo().plus(Duration.ofHours(1))))
                        .toList();
    }

    public boolean isAvailable(Long communityHomeId, Instant start, Instant end) {
        var startPadded = start.minus(Duration.ofHours(1));
        var endPadded = end.plus(Duration.ofHours(1));
        var contracts = contractService.findAllBetweenStartAndEnd(communityHomeId, startPadded, endPadded);

        var numOfOverlappingContracts = contracts.stream()
                                                 .map(Contract::getReservation)
                                                 .map(reservation -> new TimeRange(reservation.getDatetimeFrom(), reservation.getDatetimeTo()))
                                                 .filter(timeRange -> this.isTouchingEnds(timeRange, startPadded, endPadded)).count();

        return numOfOverlappingContracts == 0;
    }

    private boolean isTouchingEnds(TimeRange range, Instant start, Instant end) {
        return range.end().equals(start) || range.start().equals(end);
    }

}
