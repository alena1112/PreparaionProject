package com.alena.jewelryproject.model;

import com.alena.jewelryproject.model.enums.EmailMessageToType;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Table(name = "email_message")
public class EmailMessage extends IdentifiableEntity {
    @Column(name = "message")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private EmailMessageToType type;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public EmailMessageToType getType() {
        return type;
    }

    public void setType(EmailMessageToType type) {
        this.type = type;
    }
}
