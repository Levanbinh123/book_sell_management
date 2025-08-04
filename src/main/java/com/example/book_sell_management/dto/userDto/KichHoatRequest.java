package com.example.book_sell_management.dto.userDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class KichHoatRequest {
    @NotBlank(message = "khong duoc de trong")
    String email;
    @NotBlank(message = "khong duoc de trong")
    String maKichHoat;
}
