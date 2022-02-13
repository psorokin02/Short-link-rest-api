package com.example.shortlinks.linkService;

import com.example.shortlinks.data.ShortLink;
import com.example.shortlinks.hashcode.URLHashCoder;
import com.example.shortlinks.linkDAO.LinkDAO;
import org.springframework.stereotype.Service;

@Service
public class LinkService {
    LinkDAO linkDAO;
    URLHashCoder urlHashCoder;

    public String getURLByHash(String hash){
        return urlHashCoder.decodeURL(hash);
    }

    public String getHashByURL(String URL){
        return urlHashCoder.encodeURL(URL);
    }

    public ShortLink getShortLinkByHash(String hash){
        return linkDAO.getLinkByHash(hash);
    }

    public ShortLink getShortLinkByURL(String URL){
        return linkDAO.getLinkByHash(getHashByURL(URL));
    }

    public ShortLink addShortLink(String URL){
        ShortLink link = new ShortLink(getHashByURL(URL));
        return linkDAO.saveLink(link);
    }

    public ShortLink updateShortLink(String URL){
        ShortLink link = new ShortLink(getHashByURL(URL));
        return linkDAO.saveLink(link);
    }

    public ShortLink updateShortLink(ShortLink shortLink){
        return linkDAO.saveLink(shortLink);
    }

    public LinkService(LinkDAO linkDAO, URLHashCoder urlHashCoder) {
        this.linkDAO = linkDAO;
        this.urlHashCoder = urlHashCoder;
    }
}
