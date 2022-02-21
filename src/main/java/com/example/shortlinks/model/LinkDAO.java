package com.example.shortlinks.model;

import com.example.shortlinks.model.ShortLink;

public interface LinkDAO {
    public ShortLink getLinkByHash(String hash);
    public ShortLink saveLink(ShortLink link);
}
