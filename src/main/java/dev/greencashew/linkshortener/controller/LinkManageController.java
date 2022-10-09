package dev.greencashew.linkshortener.controller;

import dev.greencashew.linkshortener.dto.LinkDto;
import dev.greencashew.linkshortener.link.LinkService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/links")

class LinkManageController {
    private final LinkService linkService;

   // LinkManageController(final LinkService linkService) {
  //      this.linkService = linkService;
 //   }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    LinkDto createLink(@RequestBody CreateLinkDto link) {
        return linkService.createLink(link.toDto());
    }

}
