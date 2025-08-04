package com.example.book_sell_management.repository;

import com.example.book_sell_management.dto.userDto.DangKyRequest;
import com.example.book_sell_management.entity.NguoiDung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NguoiDungRepository extends JpaRepository<NguoiDung, Integer> {
    boolean existsByTenDangNhap(String tenDangNhap);
    boolean existsByEmail(String email);
    NguoiDung findByTenDangNhap(String tenDangNhap);
    NguoiDung findByEmail(String email);
    @Query("select s.nguoiDung from  SachYeuThich s where s.sach.maSach=:sachId")
    List<NguoiDung> findNguoiDungBySachYeuThichSach(@Param("sachId") int sachId);
    NguoiDung findByMatKhau(String matKhau);
    void deleteById(int id);
}
