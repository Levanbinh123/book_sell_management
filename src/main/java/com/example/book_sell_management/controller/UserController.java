package com.example.book_sell_management.controller;
import com.example.book_sell_management.dto.userDto.NguoiDungDTO;
import com.example.book_sell_management.dto.userDto.UpdateUser;
import com.example.book_sell_management.entity.NguoiDung;
import com.example.book_sell_management.repository.NguoiDungRepository;
import com.example.book_sell_management.request.ThongBao;
import com.example.book_sell_management.security.JwtService;
import com.example.book_sell_management.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Tag(name = "02 - Quản lý người dùng", description = "Thông tin tài khoản, phân quyền")
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Operation(summary ="xoa nguoi dung")
    @DeleteMapping("/{id}")//admin
    public ResponseEntity<Void> deleteUserById(@PathVariable Integer id) throws Exception{
         userService.deleteUserById(id);
         return ResponseEntity.ok().build();
    }
    @PostMapping//admin
    public ResponseEntity<ThongBao>themNguoiDung(@RequestBody NguoiDung nguoiDung) throws Exception {
        ResponseEntity<?> addNguoiDung=userService.themNguoiDung(nguoiDung);
        if(addNguoiDung.getStatusCode()==HttpStatus.INTERNAL_SERVER_ERROR){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ThongBao("loi server"));
        }
        return ResponseEntity.ok().body(new ThongBao("da them thanh cong nguoi dung"));
    }
    @GetMapping//admin
    public ResponseEntity<List<NguoiDung>> findAllNguoiDung(){

        List<NguoiDung> listNGuoiDung= userService.findAllNguoiDung();
        if(listNGuoiDung.isEmpty()){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.ok().body(listNGuoiDung);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Optional<NguoiDung>> updateNguoiDung(@RequestBody UpdateUser updateUser,@PathVariable int id) throws Exception{
        Optional<NguoiDung> nguoiDung= Optional.ofNullable(userService.updateNguoiDung(updateUser, id));
        if(!nguoiDung.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().body(nguoiDung);
    }
    @Operation(summary = "Lấy thông tin người dùng qua token",description = "nhập tocken và nhận thông tin người dùng")
    @GetMapping("/gettokenuser")//public
    public ResponseEntity<NguoiDungDTO> extractUserByToken(@RequestHeader("Authorization") String token) throws Exception {
         ResponseEntity<NguoiDungDTO> nguoidung=userService.findUserByJwt(token);
         if(nguoidung.getStatusCode()==HttpStatus.INTERNAL_SERVER_ERROR){
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
         }
         if(nguoidung.getStatusCode()==HttpStatus.NOT_FOUND){
             return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
         }
         return ResponseEntity.ok().body(nguoidung.getBody());
    }

}
