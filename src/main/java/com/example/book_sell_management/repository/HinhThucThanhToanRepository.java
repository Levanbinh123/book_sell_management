package com.example.book_sell_management.repository;

import com.example.book_sell_management.entity.HinhThucGiaoHang;
import com.example.book_sell_management.entity.HinhThucThanhToan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HinhThucThanhToanRepository extends JpaRepository<HinhThucThanhToan, Integer> {
    boolean existsByTenHinhThucThanhToan(String tenHinhThucThanhToan);
    HinhThucThanhToan findByTenHinhThucThanhToan(String tenHinhThucThanhToan);
}
