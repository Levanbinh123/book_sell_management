package com.example.book_sell_management.service;

public interface EmailService {
    public void sendMessage(String from, String to, String subject, String text);
}
