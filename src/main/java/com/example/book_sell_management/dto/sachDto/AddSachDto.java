package com.example.book_sell_management.dto.sachDto;

import lombok.Data;

@Data
public class AddSachDto {
    private String tenSach;
    private String tenTacGia;
    private String ISBN;
    private String moTa;
    private double giaNiemYet;
    private double giaBan;
    private int soLuong;

}
