package dev.greencashew.linkshortener.link;

import dev.greencashew.linkshortener.dto.LinkDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class LinkEntity {

    @Id
    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String targetUrl;

    private LocalDate expirationDate;
    private int visits;

    LinkDto toDto() {
        return new LinkDto(
                id,
                email,
                targetUrl,
                expirationDate,
                visits
        );
    }

    static LinkEntity fromDto(LinkDto linkDto) {
        return new LinkEntity(
                linkDto.id(),
                linkDto.email(),
                linkDto.targetUrl(),
                linkDto.expirationDate(),
                linkDto.visits()
        );
    }

}
