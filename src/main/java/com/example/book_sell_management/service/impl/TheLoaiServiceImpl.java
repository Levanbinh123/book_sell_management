package com.example.book_sell_management.service.impl;

import com.example.book_sell_management.entity.Sach;
import com.example.book_sell_management.entity.TheLoai;
import com.example.book_sell_management.repository.TheLoaiRepository;
import com.example.book_sell_management.service.TheLoaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TheLoaiServiceImpl implements TheLoaiService {
    @Autowired
   private TheLoaiRepository theLoaiRepository;
    @Override
    public TheLoai getTheLoaiById(Integer id) {
        return theLoaiRepository.findById(id).get();
    }

    @Override
    public List<TheLoai> getAllTheLoai() {
        return theLoaiRepository.findAll();
    }

    @Override
    public TheLoai createTheLoai(TheLoai theLoai) {
        return theLoaiRepository.save(theLoai);
    }

    @Override
    public TheLoai updateTheLoai(Integer id, TheLoai theLoai) throws Exception {
        Optional<TheLoai> updateTheLoai = theLoaiRepository.findById(id);
        if (!updateTheLoai.isPresent()) {
            throw new Exception("not found id");
        }
        updateTheLoai.get().setTenTheLoai(theLoai.getTenTheLoai());
        return theLoaiRepository.save(updateTheLoai.get());
    }

    @Override
    public void deleteTheLoaiById(Integer id) throws Exception {
        Optional<TheLoai> optional = theLoaiRepository.findById(id);
        if (optional == null) {
            throw new Exception("khong tim thay id");
        }
        theLoaiRepository.deleteById(id);

    }
    @Override
    public List<Sach> getSachByTheLoaiId(Integer theLoaiId) {
        return theLoaiRepository.getSachByMaTheLoai(theLoaiId);
    }
}
