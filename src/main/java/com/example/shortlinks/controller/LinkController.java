package com.example.shortlinks.controller;

import com.example.shortlinks.data.ShortLink;
import com.example.shortlinks.linkService.LinkService;
import com.example.shortlinks.security.token.TokenProvider;
import com.example.shortlinks.security.user.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/nord")
public class LinkController {

    LinkService linkService;
    TokenProvider tokenProvider;

    @GetMapping(path = "/{hash}")
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
