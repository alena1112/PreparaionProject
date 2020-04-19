package com.alena.jewelryproject.service.sitemap;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Urlset {
    private List<Url> url;
    @XmlAttribute
    private final String xmlns = "http://www.sitemaps.org/schemas/sitemap/0.9";
    @XmlAttribute(name = "xmlns:xsi")
    private final String xsi = "http://www.w3.org/2001/XMLSchema-instance";
    @XmlAttribute(name = "xsi:schemaLocation")
    private final String schemaLocation = "http://www.sitemaps.org/schemas/sitemap/0.9 " +
            "http://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd";

    public Urlset() {
    }

    public Urlset(List<Url> url) {
        this.url = url;
    }

    public List<Url> getUrl() {
        return url;
    }

    @XmlElement
    public void setUrl(List<Url> url) {
        this.url = url;
    }
}
