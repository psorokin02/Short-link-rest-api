package com.example.shortlinks.model;

import com.example.shortlinks.model.ShortLink;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class LinkDAORepository implements LinkDAO{

    EntityManager entityManager;

    @Override
    @Transactional
    public ShortLink getLinkByHash(String hash) {
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createNativeQuery("select * from links where hash=:hash", ShortLink.class);
        query.setParameter("hash", hash);
        List<ShortLink> links = query.getResultList();
        return (links.isEmpty()) ? null : links.get(0);
    }

    @Override
    @Transactional
    public ShortLink saveLink(ShortLink link) {
        Session session = entityManager.unwrap(Session.class);
            session.saveOrUpdate(link);
        return getLinkByHash(link.getHash());
    }

    public LinkDAORepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
