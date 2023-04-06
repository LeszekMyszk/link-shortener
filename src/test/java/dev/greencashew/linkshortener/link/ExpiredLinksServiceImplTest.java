package dev.greencashew.linkshortener.link;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class ExpiredLinksServiceImplTest {

    @Mock
    LinkRepository repository;
    @InjectMocks
    ExpiredLinksServiceImpl expiredLinksService;


       private static final LinkEntity.LinkEntityBuilder LINK_ENTITY_BUILDER = LinkEntity.builder()
            .email("email@email.com")
            .targetUrl("http://google.com")
            .visits(0);

    @Test
    void shouldRemoveExpiredLinks() {
        //given
        final LocalDate today = LocalDate.of(2022, 1, 1);
        LinkEntity expiredLink1 = LINK_ENTITY_BUILDER.id("id1").expirationDate(today.minusDays(20)).build();
        LinkEntity expiredLink2 = LINK_ENTITY_BUILDER.id("id2").expirationDate(today.minusDays(50)).build();
        LinkEntity expiredLink3 = LINK_ENTITY_BUILDER.id("id3").expirationDate(today.minusDays(1800)).build();
        List<LinkEntity> expiredLinks = List.of(expiredLink1, expiredLink2, expiredLink3);
        given(repository.findLinksBeforeDate(today)).willReturn(expiredLinks);

        //when
        expiredLinksService.removeExpiredLinks(today);

        //then
        then(repository).should().deleteAll(expiredLinks);
    }

    @Test
    void shouldNotRemoveUnExpiredLinks() {
        //given
        final LocalDate today = LocalDate.of(2022, 1, 1);

        List<LinkEntity> nonExpiredLinks = List.of();
        given(repository.findLinksBeforeDate(today)).willReturn(nonExpiredLinks);

        //when
        expiredLinksService.removeExpiredLinks(today);

        //then
        then(repository).should().deleteAll(nonExpiredLinks);
    }
}