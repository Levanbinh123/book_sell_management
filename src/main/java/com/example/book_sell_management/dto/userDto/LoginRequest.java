package com.example.book_sell_management.dto.userDto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotBlank(message = "khong duoc de trong")
    String username;
    @NotBlank(message = "khong duoc de trong")
    String password;
}
