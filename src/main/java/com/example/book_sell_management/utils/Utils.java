package com.example.book_sell_management.utils;

import java.util.UUID;

public class Utils {
    public static  String taoMaKichHoat(){
        // Tạo mã ngẫu nhiên
        return UUID.randomUUID().toString();
    }
}
