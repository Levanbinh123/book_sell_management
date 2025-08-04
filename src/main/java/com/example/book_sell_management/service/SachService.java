package com.example.book_sell_management.service;

import com.example.book_sell_management.dto.sachDto.AddSachDto;
import com.example.book_sell_management.dto.sachDto.UpdateSachDto;
import com.example.book_sell_management.entity.Sach;

import java.util.List;
import java.util.Optional;

public interface SachService {
    Optional<Sach> findSachById(int id);
    List<Sach> findAllSach();
    Sach updateSach(Integer id,UpdateSachDto updateSachDto) throws Exception;
    Sach addSach(AddSachDto addSachDto);
    void deleteSachById(Integer id);
    List<Sach> findByTenContains(String keyword);         // Tìm sách theo tên (tìm kiếm)
    List<Sach> findByTheLoai(String theLoai);             // 🔍 Lọc theo thể loại
    List<Sach> findByTacGia(String tacGia);               //  Lọc theo tác giả
    List<Sach> findByGiaBetween(double min, double max);  //  Tìm theo khoảng giá
    List<Sach> findTop10MoiNhat();                        //  Hiển thị sách mới nhất
    List<Sach> findTop10BanChay();                        //  Sách bán chạy
    void tangSoLuongDaBan(int id, int soLuong);

}
