package com.example.book_sell_management.service.impl;

import com.example.book_sell_management.entity.NguoiDung;
import com.example.book_sell_management.entity.Sach;
import com.example.book_sell_management.entity.SachYeuThich;
import com.example.book_sell_management.repository.NguoiDungRepository;
import com.example.book_sell_management.repository.SachRepository;
import com.example.book_sell_management.repository.SachYeuThichRepository;
import com.example.book_sell_management.service.DanhSachYeuThichService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DanhSachYeuThichServiceImpl implements DanhSachYeuThichService {
    @Autowired
    private SachYeuThichRepository sachYeuThichRepository;
    @Autowired
    private NguoiDungRepository nguoiDungRepository;
    @Autowired
    private SachRepository sachRepository;
    @Override
    public void addToYeuThich(String tenDangNhap, int sachId) {
    NguoiDung nguoiDung=nguoiDungRepository.findByTenDangNhap(tenDangNhap);
    Sach sach=sachRepository.findById(sachId).orElseThrow(()->new RuntimeException(" not found sach"));
    if(!sachYeuThichRepository.existsByNguoiDungAndSach(nguoiDung, sach)){
        SachYeuThich sachYeuThich=new SachYeuThich();
        sachYeuThich.setNguoiDung(nguoiDung);
        sachYeuThich.setSach(sach);
        sachYeuThichRepository.save(sachYeuThich);
    }
    }

    @Override
    @Transactional
    public void removeFromYeuThich(String tenDangNhap, int sachId) {
        NguoiDung nguoiDung=nguoiDungRepository.findByTenDangNhap(tenDangNhap);
        Sach sach = sachRepository.findById(sachId).orElseThrow();
        sachYeuThichRepository.deleteByNguoiDungAndSach(nguoiDung, sach);
    }

    @Override
    public void clearYeuThich(String tenDangNhap) {
        NguoiDung nguoiDung = nguoiDungRepository.findByTenDangNhap(tenDangNhap);
        sachYeuThichRepository.deleteByNguoiDung(nguoiDung);

    }

    @Override
    public List<SachYeuThich> getDanhSachYeuThich() {
        return sachYeuThichRepository.findAll();
    }

    @Override
    public Page<Sach> getYeuThichWithPaging(int nguoiDungId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return sachYeuThichRepository.findSachsByNguoiDungId(nguoiDungId, pageable);
    }

    @Override
    public int getCountYeuThichByNguoiDung(int nguoiDungId) {
        NguoiDung nguoiDung=nguoiDungRepository.findById(nguoiDungId).orElseThrow();
        return sachYeuThichRepository.countByNguoiDung(nguoiDung);
    }

    @Override
    public List<NguoiDung> getNguoiDungYeuThichSach(int sachId) {
        return sachYeuThichRepository.findNguoiDungBySachId(sachId);
    }
}
