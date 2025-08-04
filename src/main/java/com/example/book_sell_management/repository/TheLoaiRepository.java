package com.example.book_sell_management.repository;

import com.example.book_sell_management.entity.Sach;
import com.example.book_sell_management.entity.TheLoai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TheLoaiRepository extends JpaRepository<TheLoai, Integer> {
    @Query("select s from TheLoai t JOIN t.danhSachQuyenSach s where t.maTheLoai=:theLoaiId")
    List<Sach> getSachByMaTheLoai(Integer theLoaiId);
    boolean existsByTenTheLoai(String tenTheLoai);
}
