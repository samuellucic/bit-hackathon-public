package hr.bithackathon.rental.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "authority")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Authority implements GrantedAuthority {

    @Id
    private Long id;

    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
}
