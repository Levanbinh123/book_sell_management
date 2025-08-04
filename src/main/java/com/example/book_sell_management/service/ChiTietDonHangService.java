package com.example.book_sell_management.service;

import com.example.book_sell_management.entity.ChiTietDonHang;

import java.util.List;

public interface ChiTietDonHangService {
    ChiTietDonHang addChiTietDonHang(ChiTietDonHang chiTietDonHang);

    // Cập nhật chi tiết đơn hàng
    ChiTietDonHang updateChiTietDonHang(ChiTietDonHang chiTietDonHang);

    // Xoá chi tiết đơn hàng theo ID
    void deleteChiTietDonHangById(long id);

    // Lấy chi tiết đơn hàng theo ID
    ChiTietDonHang getChiTietDonHangById(long id);

    // Lấy danh sách tất cả chi tiết đơn hàng
    List<ChiTietDonHang> getAllChiTietDonHang();

    // Lấy danh sách chi tiết đơn hàng theo mã đơn hàng
    List<ChiTietDonHang> getChiTietDonHangByDonHangId(int donHangId);

    // Lấy danh sách chi tiết đơn hàng theo mã sách
    List<ChiTietDonHang> getChiTietDonHangBySachId(int sachId);

    // Tính tổng tiền cho một đơn hàng
    double getTongTienByDonHangId(int donHangId);
}
