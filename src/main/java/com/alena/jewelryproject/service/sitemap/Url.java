package com.alena.jewelryproject.service.sitemap;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
public class Url {
    private String loc;
    private Date lastmod;
    private Double priority;

    public Url() {
    }

    public Url(String loc, Date lastmod, Double priority) {
        this.loc = loc;
        this.lastmod = lastmod;
        this.priority = priority;
    }

    public String getLoc() {
        return loc;
    }

    @XmlElement
    public void setLoc(String loc) {
        this.loc = loc;
    }

    public Date getLastmod() {
        return lastmod;
    }

    @XmlElement
    public void setLastmod(Date lastmod) {
        this.lastmod = lastmod;
    }

    public Double getPriority() {
        return priority;
    }

    @XmlElement
    public void setPriority(Double priority) {
        this.priority = priority;
    }
}
