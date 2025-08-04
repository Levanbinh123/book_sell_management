package com.example.book_sell_management.repository;

import com.example.book_sell_management.entity.HinhThucGiaoHang;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HinhThucGiaoHangRepository extends JpaRepository<HinhThucGiaoHang, Integer> {
    boolean existsByTenHinhThucGiaoHang(String tenHinhThucGiaoHang);

    HinhThucGiaoHang findByTenHinhThucGiaoHang(String tenHinhThucGiaoHang);
}
