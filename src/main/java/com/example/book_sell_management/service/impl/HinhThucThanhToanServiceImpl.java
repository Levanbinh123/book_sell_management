package com.example.book_sell_management.service.impl;

import com.example.book_sell_management.entity.HinhThucThanhToan;
import com.example.book_sell_management.repository.HinhThucThanhToanRepository;
import com.example.book_sell_management.service.HinhThucThanhToanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HinhThucThanhToanServiceImpl implements HinhThucThanhToanService {
    @Autowired
    private HinhThucThanhToanRepository hinhThucThanhToanRepository;
    @Override
    public List<HinhThucThanhToan> getAll() {
        return hinhThucThanhToanRepository.findAll();
    }
    @Override
    public HinhThucThanhToan getById(int id) {
        return hinhThucThanhToanRepository.findById(id).orElse(null);
    }

    @Override
    public HinhThucThanhToan create(HinhThucThanhToan dto) {
       if(dto.getTenHinhThucThanhToan().equals(com.example.book_sell_management.enumm.HinhThucThanhToan.THANH_TOAN_TRUC_TIEP)){
           dto.setTenHinhThucThanhToan("THANH_TOAN_TRUC_TIEP");
       } else if (dto.getTenHinhThucThanhToan().equals(com.example.book_sell_management.enumm.HinhThucThanhToan.MOMO)) {
           dto.setTenHinhThucThanhToan("MOMO");
       }
       else {
           throw new RuntimeException("not implemented yet");
       }
       return hinhThucThanhToanRepository.save(dto);
    }

    @Override
    public HinhThucThanhToan update(String tenHinhThucThanhToan, HinhThucThanhToan dto) {
        HinhThucThanhToan h1=hinhThucThanhToanRepository.findByTenHinhThucThanhToan(tenHinhThucThanhToan);
        h1.setMoTa(dto.getMoTa());
        h1.setChiPhiThanhToan(dto.getChiPhiThanhToan());
        return hinhThucThanhToanRepository.save(h1);
    }

    @Override
    public void delete(int id) {
        hinhThucThanhToanRepository.deleteById(id);

    }

    @Override
    public boolean existsByTen(String ten) {
        return hinhThucThanhToanRepository.existsByTenHinhThucThanhToan(ten);
    }

    @Override
    public List<HinhThucThanhToan> findByChiPhiLessThan(double maxChiPhi) {
        return hinhThucThanhToanRepository.findAll().stream().filter(h->h.getChiPhiThanhToan()<maxChiPhi).collect(Collectors.toList());
    }

    @Override
    public List<HinhThucThanhToan> sortByChiPhi(boolean asc) {
        return hinhThucThanhToanRepository.findAll(Sort.by(Sort.Direction.DESC, "chiPhiThanhToan"));
    }

    @Override
    public List<HinhThucThanhToan> searchByKeyword(String keyword) {
        return hinhThucThanhToanRepository.findAll().stream().filter(h->h.getTenHinhThucThanhToan().toLowerCase().contains(keyword.toLowerCase())).collect(Collectors.toList());
    }
}
