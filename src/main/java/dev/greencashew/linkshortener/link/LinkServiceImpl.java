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
        final String lowerCaseId = toDto.id().toLowerCase();

        if (linkRepository.findById(lowerCaseId).isPresent()) {
            throw new LinkAlreadyExistsException(toDto.id());
        }
        final LinkEntity entity = LinkEntity.fromDto(toDto);
        entity.setId(lowerCaseId);
        linkRepository.save(entity);
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

    @Override
    public LinkDto getLinksById(final String id) {
        final String lowerCaseId = id.toLowerCase();
        return linkRepository.findById(id)
                .orElseThrow(() -> new LinkNotFoundException(lowerCaseId))
                .toDto();
    }

    @Override
    public boolean deleteLink(final String id, final String email) {
        final String lowerCaseId = id.toLowerCase();
        final LinkDto linkDto = getLinksById(id);
        if (linkDto.email().equalsIgnoreCase(email)) {
            linkRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
