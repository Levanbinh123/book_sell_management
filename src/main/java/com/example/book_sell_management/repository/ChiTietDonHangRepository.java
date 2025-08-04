package com.example.book_sell_management.repository;

import com.example.book_sell_management.entity.ChiTietDonHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChiTietDonHangRepository extends JpaRepository<ChiTietDonHang, Integer> {

    List<ChiTietDonHang> findByDonHang_MaDonHang(Integer maDonHang);
    Void deleteByDonHang_MaDonHang(Integer maDonHang);
}
