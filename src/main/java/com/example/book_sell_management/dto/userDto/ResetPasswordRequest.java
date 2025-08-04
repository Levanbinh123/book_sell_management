package com.example.book_sell_management.dto.userDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResetPasswordRequest {
    @NotBlank(message = "khong duoc de trong")
    private String email;
    @NotBlank(message = "khong duoc de trong")
   private String oldPassword;
    @NotBlank(message = "khong duoc de trong")
    private String newPassWord;
}
