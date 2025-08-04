package com.example.book_sell_management.service;

import com.example.book_sell_management.entity.HinhAnh;

import java.util.List;

public interface HinhAnhService {
    HinhAnh addHinhAnh(HinhAnh hinhAnh);
    HinhAnh updateHinhAnhDaiDien(Integer sachId,Integer maHinhAnh) throws Exception;
    List<HinhAnh> getHinhAnhBySachId(Integer sachId);
    HinhAnh getHinhAnhById(Integer id);
    void deleteHinhAnhById(Integer id);

    //  Mở rộng
    void addMultipleHinhAnh(List<HinhAnh> hinhAnhs);
    List<HinhAnh> getAllHinhAnh();
    void deleteHinhAnhBySachId(Integer sachId);
    List<HinhAnh> getHinhAnhDaiDien(Integer sachId);
}
