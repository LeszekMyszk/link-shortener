package dev.greencashew.linkshortener.link;

import dev.greencashew.linkshortener.dto.LinkDto;
import dev.greencashew.linkshortener.link.api.exception.LinkAlreadyExistsException;
import dev.greencashew.linkshortener.link.api.exception.LinkNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
class LinkServiceImpl implements LinkService {


    private final LinkRepository linkRepository;


    @Override
    public LinkDto createLink(final LinkDto toDto) {
        if (linkRepository.findById(toDto.id()).isPresent()) {
            throw new LinkAlreadyExistsException(toDto.id());
        }
        linkRepository.save(LinkEntity.fromDto(toDto));
        return toDto;
    }

    @Override
    public String gatherLink(final String id) {

        final LinkEntity linkEntity = linkRepository.findById(id)
                        .orElseThrow(() -> new LinkNotFoundException());

        return linkEntity.getTargetUrl();
    }


}
