package com.example.book_sell_management.repository;

import com.example.book_sell_management.entity.DonHang;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface DonHangRepository extends JpaRepository<DonHang, Integer> {
    @Query("select d from DonHang d where d.nguoiDung.maNguoiDung=:maNguoiDung ")
    DonHang findByMaNguoiDung(Integer maNguoiDung);
    @Query("select d from DonHang d where d.nguoiDung.tenDangNhap=:tenDangNhap ")
    DonHang findByTenDangNhap(String tenDangNhap);
    List<DonHang> getDonHangByTrangThai(String trangThai);
    @Query("select d from DonHang d where d.ngayTao BETWEEN : fromDate and : toDate")
    List<DonHang> getDonHangByDateRange(Date fromDate, Date toDate);
    @Query("SELECT d FROM DonHang d WHERE d.ngayTao >= :startOfDay AND d.ngayTao <= :endOfDay AND d.trangThaiThanhToan = 'DA_THANH_TOAN'")
    List<DonHang> getDoanhThuTheoNgay(
            @Param("startOfDay") Date startOfDay,
            @Param("endOfDay") Date endOfDay
    );
}
