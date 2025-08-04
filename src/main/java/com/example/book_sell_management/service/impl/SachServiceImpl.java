package com.example.book_sell_management.service.impl;

import com.example.book_sell_management.dto.sachDto.AddSachDto;
import com.example.book_sell_management.dto.sachDto.UpdateSachDto;
import com.example.book_sell_management.entity.Sach;
import com.example.book_sell_management.repository.SachRepository;
import com.example.book_sell_management.repository.TheLoaiRepository;
import com.example.book_sell_management.service.SachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SachServiceImpl implements SachService {
    @Autowired
    private SachRepository sachRepository;
    @Autowired
    private TheLoaiRepository theLoaiRepository;
    @Override
    public Optional<Sach> findSachById(int id) {
        return sachRepository.findById(id);
    }

    @Override
    public List<Sach> findAllSach() {
        return sachRepository.findAll();
    }

    @Override
    public Sach updateSach(Integer id, UpdateSachDto updateSachDto) throws Exception {
        Sach sach = sachRepository.findById(id).get();
        if(sach == null){
            throw new Exception("not found");
        }
        sach.setGiaBan(updateSachDto.getGiaBan());
        sach.setMoTa(updateSachDto.getMoTa());
        sach.setGiaNiemYet(updateSachDto.getGiaNiemYet());
        sach.setSoLuong(updateSachDto.getSoLuong());
        sachRepository.save(sach);
        return sach;
    }

    @Override
        public Sach addSach(AddSachDto addSachDto) {
            Sach sach = new Sach();
            sach.setGiaBan(addSachDto.getGiaBan());
            sach.setMoTa(addSachDto.getMoTa());
            sach.setSoLuong(addSachDto.getSoLuong());
            sach.setTenSach(addSachDto.getTenSach());
            sach.setISBN(addSachDto.getISBN());
            sach.setTenTacGia(addSachDto.getTenTacGia());
            sach.setGiaNiemYet(addSachDto.getGiaNiemYet());
            sachRepository.save(sach);
            return sach;
        }

    @Override
    public void deleteSachById(Integer id) {
        sachRepository.deleteById(id);

    }

    @Override
    public List<Sach> findByTenContains(String keyword) {
        return sachRepository.findByTenSach(keyword);
    }

    @Override
    public List<Sach> findByTheLoai(String theLoai) {
        return sachRepository.findByTenTheLoai(theLoai);
    }

    @Override
    public List<Sach> findByTacGia(String tacGia) {
        return sachRepository.findByTenTacGia(tacGia);
    }

    @Override
    public List<Sach> findByGiaBetween(double min, double max) {
        return sachRepository.findByGiaBanBetween(min, max);
    }

    @Override
    public List<Sach> findTop10MoiNhat() {
       return null;
    }

    @Override
    public List<Sach> findTop10BanChay() {
        Pageable pageable = PageRequest.of(0, 10); // trang đầu, 10 phần tử
        return sachRepository.findTop10ByOrderBySoLuongDaBanDesc((java.awt.print.Pageable) pageable);
    }

    @Override
    public void tangSoLuongDaBan(int id, int soLuong) {

    }
}
