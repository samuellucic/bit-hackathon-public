package hr.bithackathon.rental.domain.dto;

public record CommunityHomeResponse(
    Long id,
    String name,
    String address,
    String postalCode,
    String city,
    Double area,
    Integer capacity
) {

    public static CommunityHomeResponse from(hr.bithackathon.rental.domain.CommunityHome communityHome) {
        return new CommunityHomeResponse(
            communityHome.getId(),
            communityHome.getName(),
            communityHome.getAddress(),
            communityHome.getPostalCode(),
            communityHome.getCity(),
            communityHome.getArea(),
            communityHome.getCapacity()
        );
    }

}
