package com.example.book_sell_management.dto.userDto;

import lombok.Data;

@Data
public class UpdateUser {
    private String hoDem;
    private String ten;
    private String tenDangNhap;
    private String matKhau;
    private char gioiTinh;
    private String email;
    private String soDienThoai;
}
