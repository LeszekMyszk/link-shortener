package dev.greencashew.linkshortener.link;

import dev.greencashew.linkshortener.dto.LinkDto;
import dev.greencashew.linkshortener.link.api.exception.LinkAlreadyExistsException;
import dev.greencashew.linkshortener.link.api.exception.LinkNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
class LinkServiceImpl implements LinkService {


    private final LinkRepository linkRepository;


    @Override
    @Transactional
    public LinkDto createLink(final LinkDto toDto) {
        if (linkRepository.findById(toDto.id()).isPresent()) {
            throw new LinkAlreadyExistsException(toDto.id());
        }
        linkRepository.save(LinkEntity.fromDto(toDto));
        return toDto;
    }

    @Override
    @Transactional
    public String gatherLinkAndIncrementVisits(final String id) {

        final LinkEntity linkEntity = linkRepository.findById(id)
                .orElseThrow(() -> new LinkNotFoundException());
        linkEntity.setVisits(linkEntity.getVisits()+1);
        return linkEntity.getTargetUrl();
    }

    @Override
    public List<LinkDto> getLinksForVisitsHigherThan(final Integer visits) {
        return linkRepository.findAllByVisitsGreaterThan(visits)
                .stream()
                .map(LinkEntity::toDto)
                .collect(Collectors.toList());
    }


}
