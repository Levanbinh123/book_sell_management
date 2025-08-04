package com.example.book_sell_management.repository;

import com.example.book_sell_management.entity.SuDanhGia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SuDanhGiaRepository extends JpaRepository<SuDanhGia, Integer> {

    @Query("SELECT s FROM SuDanhGia s WHERE s.sach.maSach = :sachId")
    List<SuDanhGia>getDanhGiaBySachId(Integer sachId);
    @Query("SELECT s FROM SuDanhGia s WHERE s.nguoiDung.maNguoiDung = :userId")
    List<SuDanhGia>getDanhGiaByNguoiDungId(Integer userId);
}
