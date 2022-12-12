package com.kronsoft.pharma.notifications;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
public class FirebaseInitializer {
    Logger logger = LoggerFactory.getLogger(FirebaseInitializer.class);
    private final String firebaseConfigPath;

    FirebaseInitializer(@Value("${pharma.app.firebaseConfigFile}") String firebaseConfigPath) {
        this.firebaseConfigPath = firebaseConfigPath;
    }

    @PostConstruct
    void initialize() {
        try {
            var options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())).build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                logger.info("Firebase application has been initialized");
            }
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
    }
}
