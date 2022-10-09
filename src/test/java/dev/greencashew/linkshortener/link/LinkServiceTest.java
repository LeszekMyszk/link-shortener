package dev.greencashew.linkshortener.link;

import dev.greencashew.linkshortener.dto.LinkDto;
import dev.greencashew.linkshortener.link.exception.LinkAlreadyExistsException;
import dev.greencashew.linkshortener.link.exception.LinkNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LinkServiceTest {
    private LinkService linkService;


    @BeforeEach
    void setUp() {
        final HashMap<String, LinkDto> stringLinkDtoHashMap = new HashMap<>();
        linkService = new LinkServiceImpl();
    }

    @Test
    void shouldSaveAndRetreiveLink() {
        //given
        final LinkDto linkDto = new LinkDto("id","email", "targetUrl", null,0);

        //when
        final LinkDto resultLink = linkService.createLink(linkDto);
        final String resultTargetUrl = linkService.gatherLink(linkDto.id());

        //then
        assertEquals(linkDto.id(),resultLink.id());
        assertEquals(linkDto.targetUrl(),resultTargetUrl);
    }

    @Test
    void shouldThrowExceptionIfLinkNotFound() {
        //given
        String notExistingId = "cokolwiek";

        //when

        //then
        assertThrows(LinkNotFoundException.class, ()-> linkService.gatherLink(notExistingId));
    }

    /*@Test
    void shouldCheckIfLinkAlreadyExist() {
        //given
        LinkDto linkDto = new LinkDto("duplikat", "email", "targetUrl", null,0);
        LinkDto linkDto2 = new LinkDto("duplikat", "email", "targetUrl", null,0);
        service.createLink(linkDto);
        //when

        //then
        assertThrows(LinkAlreadyExistsException.class, () -> service.createLink(linkDto2));
        return createLink;
    }

    private void AssertThrows(final Class<LinkAlreadyExistsException> linkAlreadyExistsExceptionClass, final Object o) {
    }*/

    @Test
    @DisplayName("Should throw link not found exception when link with same id already added")
    void shouldThrowLinkAlreadyExistsExceptionWhenLinkWithSameIdAlreadyAdded() {
        //given
        String duplicatedId = "id";
        linkService.createLink(new LinkDto(duplicatedId, "emal", "target", null, 0));
        //when //then
        assertThrows(LinkAlreadyExistsException.class, () -> linkService.createLink(new LinkDto(duplicatedId, "another email", "another target", null, 0)));
    }

}