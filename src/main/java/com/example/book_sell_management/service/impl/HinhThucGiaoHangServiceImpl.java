package com.example.book_sell_management.service.impl;

import com.example.book_sell_management.entity.HinhThucGiaoHang;
import com.example.book_sell_management.entity.HinhThucThanhToan;
import com.example.book_sell_management.repository.HinhThucGiaoHangRepository;
import com.example.book_sell_management.service.HinhThucGiaoHangService;
import com.example.book_sell_management.service.HinhThucThanhToanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HinhThucGiaoHangServiceImpl implements HinhThucGiaoHangService {
    @Autowired
    private HinhThucGiaoHangRepository hinhThucGiaoHangRepository;
    @Override
    public List<HinhThucGiaoHang> getAll() {
        return hinhThucGiaoHangRepository.findAll();
    }
    @Override
    public HinhThucGiaoHang getById(int id) {
        return hinhThucGiaoHangRepository.findById(id).orElse(null);
    }
    @Override
    public ResponseEntity<HinhThucGiaoHang> create(HinhThucGiaoHang dto) {
        if(dto.getTenHinhThucGiaoHang().equals(com.example.book_sell_management.enumm.HinhThucGiaoHang.HOA_TOC)){
            dto.setTenHinhThucGiaoHang("HOA_TOC");
        }
        else if (dto.getTenHinhThucGiaoHang().equals(com.example.book_sell_management.enumm.HinhThucGiaoHang.TIET_KIEM)){
            dto.setTenHinhThucGiaoHang("TIET_KIEM");
        }
        else {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(hinhThucGiaoHangRepository.save(dto), HttpStatus.CREATED);
    }

    @Override
    public HinhThucGiaoHang update(String tenHinhThucGiaoHang, HinhThucGiaoHang dto) throws Exception {
        HinhThucGiaoHang old = hinhThucGiaoHangRepository.findByTenHinhThucGiaoHang(tenHinhThucGiaoHang);
        old.setMoTa(dto.getMoTa());
        old.setChiPhiGiaoHang(dto.getChiPhiGiaoHang());
        return hinhThucGiaoHangRepository.save(old);
    }

    @Override
    public void delete(int id) {
        hinhThucGiaoHangRepository.deleteById(id);

    }

    @Override
    public boolean existsByTen(String ten) {
        return hinhThucGiaoHangRepository.existsByTenHinhThucGiaoHang(ten);
    }

    @Override
    public List<HinhThucGiaoHang> findByChiPhiLessThan(double gia) {
        return hinhThucGiaoHangRepository.findAll().stream().filter(h->h.getChiPhiGiaoHang()<gia).collect(Collectors.toList());
    }

    @Override
    public List<HinhThucGiaoHang> sortByChiPhi(boolean asc) {
        return hinhThucGiaoHangRepository.findAll(Sort.by(asc?Sort.Direction.ASC:Sort.Direction.DESC,"chiPhiGiaoHang"));
    }

    @Override
    public List<HinhThucGiaoHang> searchByKeyword(String keyword) {
        return hinhThucGiaoHangRepository.findAll().stream().filter(h->h.getMoTa().contains(keyword)).collect(Collectors.toList());
    }
}
