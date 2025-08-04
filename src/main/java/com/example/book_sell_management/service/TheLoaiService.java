package com.example.book_sell_management.service;

import com.example.book_sell_management.entity.Sach;
import com.example.book_sell_management.entity.TheLoai;

import java.util.List;

public interface TheLoaiService {
    TheLoai getTheLoaiById(Integer id);
    List<TheLoai> getAllTheLoai();
    TheLoai createTheLoai(TheLoai theLoai);
    TheLoai updateTheLoai(Integer id,TheLoai theLoai) throws Exception;
    void deleteTheLoaiById(Integer id) throws Exception;

    // Quan hệ với sách
    List<Sach> getSachByTheLoaiId(Integer theLoaiId);


}
