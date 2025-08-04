package com.example.book_sell_management.dto.userDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ForgetPasswordRequest {
    @Email(message = "email khong hop le")
    private String email;
    @NotBlank(message = "khong duoc de trong")
    private String maKichHoat;
    @NotBlank(message = "khong duoc de trong")
    private String newPassword;
}
