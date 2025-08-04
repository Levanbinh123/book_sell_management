package com.example.book_sell_management.repository;

import com.example.book_sell_management.entity.NguoiDung;
import com.example.book_sell_management.entity.Sach;
import com.example.book_sell_management.entity.SachYeuThich;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface SachYeuThichRepository extends JpaRepository<SachYeuThich, Integer> {
    boolean existsByNguoiDungAndSach(NguoiDung nguoiDung, Sach sach);

    Optional<SachYeuThich> findByNguoiDungAndSach(NguoiDung nguoiDung, Sach sach);

    List<SachYeuThich> findByNguoiDung(NguoiDung nguoiDung);

    int countByNguoiDung(NguoiDung nguoiDung);

    void deleteByNguoiDungAndSach(NguoiDung nguoiDung, Sach sach);

    void deleteByNguoiDung(NguoiDung nguoiDung);

    @Query("SELECT syt.sach FROM SachYeuThich syt WHERE syt.nguoiDung.maNguoiDung = :nguoiDungId")
    Page<Sach> findSachsByNguoiDungId(@Param("nguoiDungId") int nguoiDungId, Pageable pageable);

    @Query("SELECT syt.nguoiDung FROM SachYeuThich syt WHERE syt.sach.maSach = :sachId")
    List<NguoiDung> findNguoiDungBySachId(@Param("sachId") int sachId);
}
