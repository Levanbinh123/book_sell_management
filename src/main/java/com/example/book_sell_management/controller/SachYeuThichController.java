package com.example.book_sell_management.controller;
import com.example.book_sell_management.entity.NguoiDung;
import com.example.book_sell_management.entity.Sach;
import com.example.book_sell_management.security.JwtService;
import com.example.book_sell_management.service.DanhSachYeuThichService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Tag(name = "07 - Quản lý danh sách yêu thích")
@RestController
@RequestMapping("/api/sachYeuThich")
public class SachYeuThichController {
    @Autowired
    private DanhSachYeuThichService danhSachYeuThichService;
    @Autowired
    private JwtService jwtService;
    @PostMapping("/sach/{sachId}")//loginuser
         public ResponseEntity<?> addSachYeuThich(@RequestHeader("Authorization") String tocken,@PathVariable Integer sachId) {
        String tenDangNhap=jwtService.getTenDangNhapFromToken(tocken);
         danhSachYeuThichService.addToYeuThich(tenDangNhap,sachId);
            return new ResponseEntity<>(HttpStatus.CREATED);}
    @DeleteMapping("/{sachId}")//loginuser
    public ResponseEntity<Void> removeFromYeuThich(@RequestHeader("Authorization") String tocken,@PathVariable int sachId) {
        String tenDangNhap=jwtService.getTenDangNhapFromToken(tocken);
        danhSachYeuThichService.removeFromYeuThich(tenDangNhap,sachId);
        return ResponseEntity.ok().build();
    }

}
