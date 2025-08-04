package com.example.book_sell_management.service;

import com.example.book_sell_management.entity.NguoiDung;
import com.example.book_sell_management.entity.Sach;
import com.example.book_sell_management.entity.SachYeuThich;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DanhSachYeuThichService {
    void addToYeuThich(String tenDangNhap, int sachId);

    void removeFromYeuThich(String tenDangNhap, int sachId);

    void clearYeuThich(String tenDangNhap);

    List<SachYeuThich> getDanhSachYeuThich();

    Page<Sach> getYeuThichWithPaging(int nguoiDungId, int page, int size);

    int getCountYeuThichByNguoiDung(int nguoiDungId);

    List<NguoiDung> getNguoiDungYeuThichSach(int sachId); // Optional
}


