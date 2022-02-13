package com.example.shortlinks.linkDAO;

import com.example.shortlinks.data.ShortLink;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class LinkDAORepository implements LinkDAO{

    EntityManager entityManager;

    @Override
    public ShortLink getLinkByHash(String hash) {
        Query query = entityManager.createNativeQuery("select * from links where hash=:" + hash);
        List<ShortLink> links = query.getResultList();
        return (links.isEmpty()) ? null : links.get(0);
    }

    @Override
    public ShortLink saveLink(ShortLink link) {
        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(link);
        return getLinkByHash(link.getHash());
    }

    public LinkDAORepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
