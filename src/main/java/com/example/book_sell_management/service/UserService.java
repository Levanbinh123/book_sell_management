package com.example.book_sell_management.service;
import com.example.book_sell_management.dto.userDto.NguoiDungDTO;
import com.example.book_sell_management.dto.userDto.UpdateUser;
import com.example.book_sell_management.entity.NguoiDung;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    public NguoiDung findByTenDangNhap(String tenDangNhap) throws Exception;
    public void deleteUserById(Integer id) throws Exception;
    public ResponseEntity<NguoiDung> themNguoiDung(NguoiDung nguoiDung) throws Exception;
    public List<NguoiDung> findAllNguoiDung();
    public NguoiDung updateNguoiDung(UpdateUser updateUser,int id) throws Exception;
    public ResponseEntity<NguoiDungDTO> findUserByJwt(String jwt) throws Exception;

}
