package com.aidanloten.centralbanker;

import com.aidanloten.centralbanker.engine.Engine;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.aidanloten.centralbanker")
public class CentralBankerApp {
    // Don't know why i need to make Engine a component to stop errors here exactly
    @Autowired
    private Engine engine;

    public static void main(String[] args) {
        SpringApplication.run(CentralBankerApp.class, args);
    }

    @PostConstruct
    private void startGameEngine() {
        engine.start();
    }

    @PreDestroy
    public void stopEngine() {
        engine.stop();
    }

}
