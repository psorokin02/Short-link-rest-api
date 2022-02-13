package com.example.shortlinks.linkDAO;

import com.example.shortlinks.data.ShortLink;

public interface LinkDAO {
    public ShortLink getLinkByHash(String hash);
    public ShortLink saveLink(ShortLink link);
}
