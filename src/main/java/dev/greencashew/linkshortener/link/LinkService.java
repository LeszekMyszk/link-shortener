package dev.greencashew.linkshortener.link;

import dev.greencashew.linkshortener.dto.LinkDto;

import java.util.List;

public interface LinkService {
    LinkDto createLink(LinkDto toDto);

    String gatherLinkAndIncrementVisits(String id);

    List<LinkDto> getLinksForVisitsHigherThan(Integer visits);

    LinkDto getLinksById(String id);

    /**
     * @param id this is alias
     * @param email used for security
     * @return returns {@code true} when link was found and deleted
     */
    boolean deleteLink(String id, String email);

}
