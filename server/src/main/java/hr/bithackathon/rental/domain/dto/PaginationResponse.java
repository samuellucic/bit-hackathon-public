package hr.bithackathon.rental.domain.dto;

import lombok.Builder;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

@Builder
public record PaginationResponse<T> (List<T> items, long totalElements, long totalPages) {

    public static <T, R> PaginationResponse<R> fromPage(Page<T> page, Function<T, R> mapper) {
        return PaginationResponse.<R>builder()
                .items(page.getContent().stream().map(mapper).toList())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }
}
