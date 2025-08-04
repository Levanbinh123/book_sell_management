package com.example.book_sell_management.repository;

import com.example.book_sell_management.entity.Sach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;

public interface SachRepository extends JpaRepository<Sach, Integer> {
    //List<Sach> findByTheLoai(String theLoai);
    List<Sach> findByTenTacGia(String tenTacGia);
    List<Sach> findByGiaBanBetween(double min, double max);
    @Query("SELECT s from Sach s ORDER BY s.soLuongDaBan DESC")
    List<Sach>  findTop10ByOrderBySoLuongDaBanDesc(Pageable pageable);
    @Query("SELECT s FROM Sach s JOIN TheLoai t WHERE t.tenTheLoai= :tenTheLoai")
    List<Sach> findByTenTheLoai( String tenTheLoai);
    List<Sach> findByTenSach(String tenSach);
}
