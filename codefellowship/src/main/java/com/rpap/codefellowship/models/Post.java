package com.rpap.codefellowship.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String body;
    private Date createdAt;
    @ManyToOne
    private SiteUser createdBy;
    //required for adding comments to post
    //@OneToMany(mappedBy = "posts", cascade = CascadeType.ALL)

    protected Post() {
    }

    public Post(String body, Date createdAt, SiteUser createdBy) {
        this.body = body;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }

    public SiteUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(SiteUser createdBy) {
        this.createdBy = createdBy;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

}
