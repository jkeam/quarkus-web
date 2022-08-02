package io.keam;

import io.keam.models.User;
import javax.transaction.Transactional;
import io.quarkus.runtime.StartupEvent;

import javax.enterprise.event.Observes;
import javax.inject.Singleton;

@Singleton
public class Startup {
    @Transactional
    public void loadUsers(@Observes StartupEvent evt) {
      System.out.println("Startup...");
        // reset and load all test users

        // User.add("user", "user", "user");
//        User.deleteAll().chain(() -> {
//            User.add("admin", "admin", "admin");
//            User.add("user", "user", "user");
//            return null;
//        });
    }
}
