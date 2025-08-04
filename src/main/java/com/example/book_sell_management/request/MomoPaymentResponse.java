package com.example.book_sell_management.request;

import lombok.Data;

@Data
public class MomoPaymentResponse {
    private String payUrl;
    private String deeplink;
    private String requestId;
    private String errorCode;
    private String message;
}