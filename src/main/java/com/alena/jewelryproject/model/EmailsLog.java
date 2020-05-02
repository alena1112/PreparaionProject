package com.alena.jewelryproject.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "emails_log")
public class EmailsLog extends IdentifiableEntity {
    @Column(name = "message")
    private String message;

    @Column(name = "from_email")
    private String from;

    @Column(name = "to_email")
    private String to;

    @Column(name = "created_date")
    private Date createdDate = new Date(System.currentTimeMillis());

    public EmailsLog(String message, String from, String to) {
        this.message = message;
        this.from = from;
        this.to = to;
    }
}
