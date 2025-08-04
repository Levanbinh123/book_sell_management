package com.example.book_sell_management.service.impl;

import com.example.book_sell_management.entity.HinhAnh;
import com.example.book_sell_management.entity.Sach;
import com.example.book_sell_management.repository.HinhAnhRepository;
import com.example.book_sell_management.repository.NguoiDungRepository;
import com.example.book_sell_management.repository.SachRepository;
import com.example.book_sell_management.service.HinhAnhService;
import com.example.book_sell_management.service.SachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class HinhAnhImpl implements HinhAnhService {
    @Autowired
    private HinhAnhRepository hinhAnhRepository;
    @Autowired
    private NguoiDungRepository nguoiDungRepository;
    @Autowired
    private SachRepository SachRepository;
    @Autowired
    private SachRepository sachRepository;

    @Override
    public HinhAnh addHinhAnh(HinhAnh hinhAnh) {
        return hinhAnhRepository.save(hinhAnh);
    }

    @Override
    public HinhAnh updateHinhAnhDaiDien(Integer sachId,Integer maHinhAnh) throws Exception {
        Optional<HinhAnh> anhMoi=hinhAnhRepository.findById(maHinhAnh);
        if(!anhMoi.isPresent()){
            throw new Exception("anh khong tom tai");
        }
        HinhAnh hinhAnh1=anhMoi.get();
        // 2. Đảm bảo ảnh mới thuộc đúng sách cần cập nhật
        if (!Objects.equals(hinhAnh1.getSach().getMaSach(), sachId)) {
            throw new Exception("Ảnh không thuộc sách có ID = " + sachId);
        }
        HinhAnh daiDienCu=hinhAnhRepository.findHinhAnhBySachIdAndIsDaiDien(sachId);
        if(daiDienCu!=null){
            daiDienCu.setIsDaiDien(false);
            hinhAnhRepository.save(daiDienCu);
        }
        hinhAnh1.setIsDaiDien(true);
        return hinhAnhRepository.save(hinhAnh1);
    }

    @Override
    public List<HinhAnh> getHinhAnhBySachId(Integer sachId) {
        return hinhAnhRepository.getHinhAnhBySachId(sachId);
    }

    @Override
    public HinhAnh getHinhAnhById(Integer id) {
        return hinhAnhRepository.findById(id).get();
    }

    @Override
    public void deleteHinhAnhById(Integer id) {
            HinhAnh hinhAnh=hinhAnhRepository.findById(id).orElse(null);
            hinhAnhRepository.delete(hinhAnh);
    }

    @Override
    public void addMultipleHinhAnh(List<HinhAnh> hinhAnhs) {
        hinhAnhRepository.saveAll(hinhAnhs);
    }

    @Override
    public List<HinhAnh> getAllHinhAnh() {
        return hinhAnhRepository.findAll();
    }

    @Override
    public void deleteHinhAnhBySachId(Integer sachId) {
        hinhAnhRepository.deleteAllHinhAnhBySachId(sachId);

    }

    @Override
    public List<HinhAnh> getHinhAnhDaiDien(Integer sachId) {
        return hinhAnhRepository.findHinhAnhisDaiDien();
    }

}
