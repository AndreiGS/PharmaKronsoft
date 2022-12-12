package com.kronsoft.pharma.notifications;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TopicNotification extends AppNotification {
    private String topic;

    TopicNotification(String topic, String title, String message) {
        super(title, message);
        this.topic = topic;
    }
}
