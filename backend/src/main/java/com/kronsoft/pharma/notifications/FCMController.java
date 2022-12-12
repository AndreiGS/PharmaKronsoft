package com.kronsoft.pharma.notifications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FCMController {
    private final FCMService fcmService;

    @Autowired
    public FCMController(FCMService fcmService) {
        this.fcmService = fcmService;
    }

    @PostMapping("/notification")
    public void sendTargetedNotification(@RequestBody DirectNotification notification){
        this.fcmService.sendNotificationToTarget(notification);
    }

    @PostMapping("/topic/notification")
    public void sendNotificationToTopic(@RequestBody TopicNotification notification){
        this.fcmService.sendNotificationToTopic(notification);
    }

    @PostMapping("/topic/subscription")
    public void subscribeToTopic(@RequestBody SubscriptionRequest subscription){
        this.fcmService.subscribeToTopic(subscription);
    }
}
