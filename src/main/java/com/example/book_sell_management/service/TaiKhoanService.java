package com.example.book_sell_management.service;

import com.example.book_sell_management.dto.userDto.*;
import org.springframework.http.ResponseEntity;

public interface TaiKhoanService {
    ResponseEntity<?> dangKyNguoiDung(DangKyRequest dangKyRequest);
    ResponseEntity<?> kichHoatTaiKhoan(KichHoatRequest kichHoatRequest);
    public ResponseEntity<?> dangNhap( LoginRequest loginRequest);
    public ResponseEntity<?> resetPassword(ResetPasswordRequest resetPasswordRequest);
    public ResponseEntity<?> forgetPassword(ForgetPasswordRequest forgetPasswordRequest);
    public ResponseEntity<?> forgetRequest(KichHoatRequest kichHoatRequest);


}
