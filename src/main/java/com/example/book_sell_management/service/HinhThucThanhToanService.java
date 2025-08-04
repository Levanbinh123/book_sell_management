package com.example.book_sell_management.service;

import com.example.book_sell_management.entity.HinhThucThanhToan;

import java.util.List;

public interface HinhThucThanhToanService {
    List<HinhThucThanhToan> getAll();
    HinhThucThanhToan getById(int id);	//Lấy chi tiết 1 hình thức
    HinhThucThanhToan create(HinhThucThanhToan dto);	//Tạo mới hình thức thanh toán
    HinhThucThanhToan update(String tenHinhThucThanhToan, HinhThucThanhToan dto);	//Cập nhật thông tin
    void delete(int id);	//Xoá một hình thức thanh toán
    boolean existsByTen(String ten);	//Kiểm tra tên đã tồn tại chưa
    List<HinhThucThanhToan> findByChiPhiLessThan(double maxChiPhi);	//Lọc theo chi phí thấp hơn X
    List<HinhThucThanhToan> sortByChiPhi(boolean asc);	//Sắp xếp theo chi phí tăng/giảm
    List<HinhThucThanhToan> searchByKeyword(String keyword);
}
