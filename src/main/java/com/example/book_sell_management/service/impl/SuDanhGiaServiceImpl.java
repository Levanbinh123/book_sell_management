package com.example.book_sell_management.service.impl;

import com.example.book_sell_management.entity.NguoiDung;
import com.example.book_sell_management.entity.Sach;
import com.example.book_sell_management.entity.SuDanhGia;
import com.example.book_sell_management.repository.NguoiDungRepository;
import com.example.book_sell_management.repository.SachRepository;
import com.example.book_sell_management.repository.SuDanhGiaRepository;
import com.example.book_sell_management.service.SuDanhGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class SuDanhGiaServiceImpl implements SuDanhGiaService {
    @Autowired
    private SuDanhGiaRepository suDanhGiaRepository;
    @Autowired
    private SachRepository sachRepository;
    @Autowired
    private NguoiDungRepository nguoiDungRepository;
    @Override
    public SuDanhGia addDanhGia(String tenDangNhap,Integer maSach,SuDanhGia danhGia) throws Exception {
        SuDanhGia themDanhGia=new SuDanhGia();
        themDanhGia.setNhanXet(danhGia.getNhanXet());
        themDanhGia.setDiemXepHang(danhGia.getDiemXepHang());
        Optional<Sach> sachDuocDanhGia=sachRepository.findById(maSach);
        NguoiDung nguoiDanhGia=nguoiDungRepository.findByTenDangNhap(tenDangNhap);
        if(sachDuocDanhGia.isEmpty()&&nguoiDanhGia==null){
            throw new Exception("not found");
        }
        themDanhGia.setNguoiDung(nguoiDanhGia);
        themDanhGia.setSach(sachDuocDanhGia.get());
        return suDanhGiaRepository.save(themDanhGia);
    }
    @Override
    public SuDanhGia updateDanhGia(String tenDangNhap,Integer maDanhGia, SuDanhGia danhGia) throws Exception {

        SuDanhGia update=suDanhGiaRepository.findById(maDanhGia).get();
        NguoiDung nguoiDanhGia=nguoiDungRepository.findByTenDangNhap(tenDangNhap);
        if(!nguoiDanhGia.equals(update.getNguoiDung())){
            throw new Exception("not found");
        }
        update.setNhanXet(danhGia.getNhanXet());
        update.setDiemXepHang(danhGia.getDiemXepHang());
        return suDanhGiaRepository.save(update);
    }

    @Override
    public void deleteDanhGiaById(Integer maDanhGia) {
        suDanhGiaRepository.deleteById(maDanhGia);
    }

    @Override
    public List<SuDanhGia> getAllDanhGia() {
        return suDanhGiaRepository.findAll();
    }

    @Override
    public List<SuDanhGia> getDanhGiaBySachId(Integer sachId) {
        return suDanhGiaRepository.getDanhGiaBySachId(sachId);
    }

    @Override
    public List<SuDanhGia> getDanhGiaByNguoiDungId(Integer nguoiDungId) {
        return suDanhGiaRepository.getDanhGiaByNguoiDungId(nguoiDungId);
    }

    @Override
    public float getDiemTrungBinhBySachId(Integer sachId) {
        List<SuDanhGia> sachDanhGia=suDanhGiaRepository.getDanhGiaBySachId(sachId);

        if (sachDanhGia.isEmpty()) {
            return 0f; // hoặc bạn có thể throw exception tùy yêu cầu
        }
        float tong=0;
        for(SuDanhGia danhGia:sachDanhGia){
            tong+=danhGia.getDiemXepHang();
        }
        return tong/sachDanhGia.size();
    }
}
