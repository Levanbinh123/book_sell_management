package com.example.book_sell_management.dto.userDto;

import lombok.Data;

@Data
public class UpdatePasswordRequest {
    private String oldPassword;
    private String newPassword;
}
