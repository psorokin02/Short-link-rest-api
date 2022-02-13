package com.example.shortlinks.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "links")
public class ShortLink {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "hash")
    private String hash;

    @Column(name = "creation")
    private Date creationDate;

    @Column(name = "expiring")
    private Date expiringDate;

    @Column(name = "number_clicks")
    private int numberOfClicks;

    public ShortLink(String hash) {
        this.hash = hash;
        this.creationDate = new Date(System.currentTimeMillis());
        this.expiringDate = new Date(System.currentTimeMillis() + (long)1e12); //31 year
        this.numberOfClicks = 0;
    }

    public void incrementNumberOfClicks(){
        numberOfClicks++;
    }

    public boolean isExpired(){
        return expiringDate.before(new Date(System.currentTimeMillis()));
    }

    public ShortLink() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getExpiringDate() {
        return expiringDate;
    }

    public void setExpiringDate(Date expiringDate) {
        this.expiringDate = expiringDate;
    }

    public int getNumberOfClicks() {
        return numberOfClicks;
    }

    public void setNumberOfClicks(int numberOfClicks) {
        this.numberOfClicks = numberOfClicks;
    }
}
