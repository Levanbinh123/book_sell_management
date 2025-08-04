package com.example.book_sell_management.controller;

import com.example.book_sell_management.dto.userDto.NguoiDungDTO;
import com.example.book_sell_management.entity.NguoiDung;
import com.example.book_sell_management.entity.Sach;
import com.example.book_sell_management.entity.SuDanhGia;
import com.example.book_sell_management.repository.NguoiDungRepository;
import com.example.book_sell_management.security.JwtService;
import com.example.book_sell_management.service.SuDanhGiaService;
import com.example.book_sell_management.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "03 - Quản lý đánh giá sách")
@RestController
@RequestMapping("/api/danhgia")
public class DanhGiaController {
    @Autowired
    private SuDanhGiaService suDanhGiaService;
    @PostMapping("/sach/{maSach}")//login user
    public ResponseEntity<SuDanhGia> addDanhGia(@RequestHeader("Authorization") String token, @PathVariable Integer maSach, @RequestBody SuDanhGia suDanhGia) throws Exception {
        String tenDangNhap=JwtService.getTenDangNhapFromToken(token);
      SuDanhGia add= suDanhGiaService.addDanhGia(tenDangNhap,maSach,suDanhGia);
       return ResponseEntity.ok(add);

    }
    @PutMapping("/{maDanhGia}")//loginuser
    public ResponseEntity<SuDanhGia> updateDanhGia(@RequestHeader("Authorization") String token, @PathVariable Integer maDanhGia, @RequestBody SuDanhGia suDanhGia) throws Exception {
        String tenDangNhap=JwtService.getTenDangNhapFromToken(token);
        SuDanhGia update= suDanhGiaService.updateDanhGia(tenDangNhap,maDanhGia,suDanhGia);
        return ResponseEntity.ok(update);

    }
    @GetMapping("/sach/{maSach}")//loginuser
    public List<SuDanhGia> getDanhGiaBySachId(@PathVariable Integer maSach){
            return suDanhGiaService.getDanhGiaBySachId(maSach);
    }
    @GetMapping("/user/{userId}")//staff
    public List<SuDanhGia> getDanhGiaByUserId(@PathVariable Integer userId){
        return suDanhGiaService.getDanhGiaByNguoiDungId(userId);
    }
    @GetMapping("/trungBinhDanhGia/sach/{sachId}")//loginuser
    public float getDiemTrungBinhBySachId(@PathVariable Integer sachId){
        return suDanhGiaService.getDiemTrungBinhBySachId(sachId);
    }
}
