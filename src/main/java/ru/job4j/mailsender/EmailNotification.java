package ru.job4j.mailsender;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private final ExecutorService pool;

    public EmailNotification() {
        pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public void emailTo(User user) {
        String name = user.getUsername();
        String email = user.getEmail();
        String subject = String.format("Notification %s to email %s", name,
                email);
        String body = String.format("Add a new event to %s", name);
        pool.submit(new Runnable() {
            @Override
            public void run() {
                send(subject, body, email);
            }
        });
    }

    public void send(String subject, String body, String email) {
    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException exc) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
