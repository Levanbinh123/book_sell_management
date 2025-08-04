package com.example.book_sell_management.repository;

import com.example.book_sell_management.entity.HinhAnh;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HinhAnhRepository extends JpaRepository<HinhAnh, Integer> {
    @Query("select h FROM HinhAnh h where h.sach.maSach=:sachId")
    List<HinhAnh> getHinhAnhBySachId(Integer sachId);
    @Query("select  h from HinhAnh h where h.sach.maSach=:sachId and h.isDaiDien = true ")
    HinhAnh findHinhAnhBySachIdAndIsDaiDien(Integer sachId);

    @Modifying
    @Transactional
    @Query("delete FROM HinhAnh h where h.sach.maSach=:sachId")
    void deleteAllHinhAnhBySachId(Integer sachId);
    @Query("select h from HinhAnh h where h.isDaiDien=true ")
    List<HinhAnh> findHinhAnhisDaiDien();

}
