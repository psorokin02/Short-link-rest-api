package com.example.shortlinks.controller;

import com.example.shortlinks.data.ShortLink;
import com.example.shortlinks.linkDAO.LinkDAO;
import com.example.shortlinks.linkService.LinkService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/nord")
public class LinkController {

    LinkService linkService;

    @GetMapping(path = "/{hash}")
    public String getOriginalURL(@PathVariable String hash){
        ShortLink link = linkService.getShortLinkByHash(hash); //null handling needed
        link.incrementNumberOfClicks();
        linkService.updateShortLink(link);
        return linkService.getURLByHash(hash);
    }

    @PostMapping(path = "/")
    public String addURL(@RequestBody String URL){
        ShortLink link = linkService.getShortLinkByURL(URL);
        if(link == null){
            link = linkService.addShortLink(URL);
        }
        return link.getHash();
    }


    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }
}
