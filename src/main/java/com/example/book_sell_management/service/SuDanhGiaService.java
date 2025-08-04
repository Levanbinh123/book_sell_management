package com.example.book_sell_management.service;

import com.example.book_sell_management.entity.SuDanhGia;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;

public interface SuDanhGiaService {
    // Thêm đánh giá mới
    SuDanhGia addDanhGia( String tenDangNhap,Integer maSach, SuDanhGia danhGia) throws Exception;

    // Cập nhật đánh giá
    SuDanhGia updateDanhGia(String tenDangNhap,Integer maDanhGia,SuDanhGia danhGia) throws Exception;

    // Xoá đánh giá theo ID
    void deleteDanhGiaById(Integer maDanhGia);

    // Lấy danh sách tất cả đánh giá
    List<SuDanhGia> getAllDanhGia();

    // Lấy danh sách đánh giá theo mã sách
    List<SuDanhGia> getDanhGiaBySachId(Integer sachId);

    // Lấy danh sách đánh giá theo mã người dùng
    List<SuDanhGia> getDanhGiaByNguoiDungId(Integer nguoiDungId);

    // Tính điểm trung bình xếp hạng của sách
    float getDiemTrungBinhBySachId(Integer sachId);
}
