package com.example.book_sell_management.service;

import com.example.book_sell_management.entity.HinhThucGiaoHang;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface HinhThucGiaoHangService {
    List<HinhThucGiaoHang> getAll();
    HinhThucGiaoHang getById(int id);
    ResponseEntity<HinhThucGiaoHang> create(HinhThucGiaoHang dto);
    HinhThucGiaoHang update(String tenHinhThucGiaoHang, HinhThucGiaoHang dto) throws Exception;
    void delete(int id);

    // Nâng cao
    boolean existsByTen(String ten);
    List<HinhThucGiaoHang> findByChiPhiLessThan(double gia);
    List<HinhThucGiaoHang> sortByChiPhi(boolean asc);
    List<HinhThucGiaoHang> searchByKeyword(String keyword);
}
