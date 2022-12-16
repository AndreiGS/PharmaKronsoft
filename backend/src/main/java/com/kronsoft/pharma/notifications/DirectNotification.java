package com.kronsoft.pharma.notifications;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class DirectNotification extends AppNotification {
    private String target;

    public DirectNotification(String target, String title, String message) {
        super(title, message);
        this.target = target;
    }
}
