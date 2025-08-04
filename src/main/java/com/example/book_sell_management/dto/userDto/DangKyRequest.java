package com.example.book_sell_management.dto.userDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DangKyRequest {
    @NotBlank(message = "khong duoc de trong")
    private String hoDem;
    @NotBlank(message = "khong duoc de trong")
    private String ten;
    @NotBlank(message = "khong duoc de trong")
    private String tenDangNhap;
    @NotBlank(message = "khong duoc de trong")
    private String matKhau;
    @NotBlank(message = "khong duoc de trong")
    private char gioiTinh;
    @Email(message = "email kkhong hop le")
    private String email;
    @NotBlank(message = "khong duoc de trong")
    private String soDienThoai;
}
