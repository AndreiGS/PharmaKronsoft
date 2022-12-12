package com.kronsoft.pharma.notifications;

import com.google.firebase.messaging.*;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;

@Service
class FCMService {
    void sendNotificationToTarget(DirectNotification notification) {
        var message = Message.builder()
                // Set the configuration for our web notification
                .setWebpushConfig(
                        // Create and pass a WebpushConfig object setting the notification
                        WebpushConfig.builder()
                                .setNotification(
                                        // Create and pass a web notification object with the specified title, body, and icon URL
                                        WebpushNotification.builder()
                                                .setTitle(notification.getTitle())
                                                .setBody(notification.getMessage())
                                                .setIcon("https://assets.mapquestapi.com/icon/v2/circle@2x.png")
                                                .build()
                                ).build()
                )
                // Specify the user to send it to in the form of their token
                .setToken(notification.getTarget())
                .build();
        FirebaseMessaging.getInstance().sendAsync(message);
    }

    void sendNotificationToTopic(TopicNotification notification){
        var message = Message.builder()
                .setWebpushConfig(
                        WebpushConfig.builder()
                                .setNotification(
                                        WebpushNotification.builder()
                                                .setTitle(notification.getTitle())
                                                .setBody(notification.getMessage())
                                                .setIcon("https://assets.mapquestapi.com/icon/v2/incident@2x.png")
                                                .build()
                                ).build()
                ).setTopic(notification.getTopic())
                .build();

        FirebaseMessaging.getInstance().sendAsync(message);
    }

    void subscribeToTopic(SubscriptionRequest subscription) {
        try {
            FirebaseMessaging.getInstance().subscribeToTopic(Collections.singletonList(subscription.getSubscriber()), subscription.getTopic());
        } catch (FirebaseMessagingException e) {
            throw new SubscriptionException(e);
        }
    }
}
