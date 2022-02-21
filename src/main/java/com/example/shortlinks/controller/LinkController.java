package com.example.shortlinks.controller;

import com.example.shortlinks.model.ShortLink;
import com.example.shortlinks.service.LinkService;
import com.example.shortlinks.security.token.TokenProvider;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/nord")
public class LinkController {

    LinkService linkService;
    TokenProvider tokenProvider;

    @GetMapping(path = "/{hash}")
    @PreAuthorize("hasAuthority('links:read')")
    public String getOriginalURL(@PathVariable String hash){
        ShortLink link = linkService.getShortLinkByHash(hash); //null handling needed
        if(link == null){
            return "No such link";
        }
        link.incrementNumberOfClicks();
        linkService.updateShortLink(link);
        return linkService.getURLByHash(hash);
    }

    @PostMapping (path = "/")
    @PreAuthorize("hasAuthority('links:write')")
    public String addURL(@RequestBody String URL){
        ShortLink link = linkService.getShortLinkByURL(URL);
        if(link == null){
            try{
                link = linkService.addShortLink(URL);
            }
            catch (Exception ex){
                return "was exception: " + ex.getMessage();
            }
        }
        return link.getHash();
    }

    public LinkController(LinkService linkService, TokenProvider tokenProvider) {
        this.linkService = linkService;
        this.tokenProvider = tokenProvider;
    }
}
