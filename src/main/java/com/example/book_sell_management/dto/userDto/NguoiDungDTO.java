package com.example.book_sell_management.dto.userDto;

import lombok.Data;

import java.util.List;
@Data
public class NguoiDungDTO {
    private String tenDangNhap;
    private String email;
    private List<String> danhSachQuyen;
}
