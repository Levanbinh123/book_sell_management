package com.example.book_sell_management.controller;

import com.example.book_sell_management.dto.userDto.*;
import com.example.book_sell_management.entity.NguoiDung;
import com.example.book_sell_management.security.JwtService;
import com.example.book_sell_management.service.TaiKhoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Tag(name = "01 - Xác thực người dùng", description = "Đăng ký, đăng nhập, OTP, đổi mật khẩu")
@RestController
@RequestMapping("/api/auth")
public class TaiKhoanController {
    @Autowired
    public TaiKhoanService taiKhoanService;
    @Operation(summary = "Đăng ký", description = "Nhập thông tin và đăng ký")
    @PostMapping("/dang-ky")
    public ResponseEntity<DangKyRequest> dangKyTaiKhoanNguoiDung(@Valid @RequestBody DangKyRequest dangKyRequest) {
       ResponseEntity<?> taikhoan=taiKhoanService.dangKyNguoiDung(dangKyRequest);
       if(taikhoan.equals(ResponseEntity.badRequest())){
           return ResponseEntity.badRequest().build();
       }
       return ResponseEntity.ok(dangKyRequest);
    }
    @PostMapping("/kich-hoat")
    public ResponseEntity<KichHoatRequest> kichHoatTaiKhoan(@Valid @RequestBody KichHoatRequest kichHoatRequest) {
         ResponseEntity<?> taiKhoanKichHoat=taiKhoanService.kichHoatTaiKhoan(kichHoatRequest);
         if(taiKhoanKichHoat.equals(ResponseEntity.badRequest())){
             return ResponseEntity.badRequest().build();
         }
         return ResponseEntity.status(200).body(kichHoatRequest);

    }
    @Operation(summary = "Đăng nhập", description = "Đăng nhập và nhận JWT token")
    @PostMapping("/dang-nhap")
    public ResponseEntity<?> dangNhap(@Valid @RequestBody LoginRequest loginRequest) {
        ResponseEntity<?> dangNhap=taiKhoanService.dangNhap(loginRequest);
        if(dangNhap.equals(ResponseEntity.badRequest())){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(dangNhap);
    }
    @Operation(summary = "Đổi mật khẩu")
    @PutMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
      ResponseEntity<?> doiMatKhau= taiKhoanService.resetPassword(resetPasswordRequest);
      if(doiMatKhau.equals(ResponseEntity.badRequest())){
          return ResponseEntity.badRequest().build();
      }
      return ResponseEntity.ok(doiMatKhau);
    }
    @Operation(summary = "Hoàn tất đổi mật khẩu",description = "nhập email và mã để hoàn tất đổi mật khẩu")
    @PostMapping("/get-maResetPassWord")
    public ResponseEntity<?> guiMaResetPassword(@Valid @RequestBody KichHoatRequest kichHoatRequest) {
        ResponseEntity<?> request= taiKhoanService.forgetRequest(kichHoatRequest);
        if(request.getBody().equals(ResponseEntity.notFound().build())){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(request.getBody());

    }
    @Operation(summary = "Quên mật khẩu")
    @PutMapping("/forgetPassword")
    public ResponseEntity<?> forgetPassword(@Valid @RequestBody ForgetPasswordRequest forgetPasswordRequest) {
        ResponseEntity<?> taiKhoan= taiKhoanService.forgetPassword(forgetPasswordRequest);
        if(taiKhoan.equals(ResponseEntity.notFound().build())){
            return ResponseEntity.notFound().build();
        } else if (taiKhoan.equals(ResponseEntity.badRequest())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(taiKhoan);
    }


}
