package com.example.book_sell_management.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.List;
@Entity
@Data
@Table(name = "don_hang")
public class DonHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_don_hang")
    private int maDonHang;
    @Column(name = "ngay_tao")
    private Date ngayTao;
    @Column(name = "dia_chi_mua_hang", length = 512)
    private String diaChiMuaHang;
    @Column(name = "dia_chi_nhan_hang", length = 512)
    private String diaChiNhanHang;
    @Column(name = "tong_tien_san_pham")
    private double tongTienSanPham;
    @Column(name = "chi_phi_giao_hang")
    private double chiPhiGiaoHang;
    @Column(name = "chi_phi_thanh_toan")
    private double chiPhiThanhToan;
    @Column(name="so_luong")
    private Integer soLuong;
    @Column(name = "tong_tien")
    private double tongTien;
    @Column(name = "trang_thai")
    private String trangThai; // VD: "CHO_XAC_NHAN", "DANG_GIAO", "DA_GIAO", "HUY"
    @Column(name="ma_thanh_toan")
    private String maThanhToan;
    @Column(name="trang_thai_thanh_toan")
    private String trangThaiThanhToan;
    @OneToMany(mappedBy = "donHang", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ChiTietDonHang> danhSachChiTietDonHang;
    @ManyToOne(cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH
    })
    @JoinColumn(name = "ma_nguoi_dung", nullable = false)
    @JsonIgnore
    private NguoiDung nguoiDung;

    @ManyToOne(cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH
    })
    @JoinColumn(name = "ma_hinh_thuc_thanh_toan")
    private HinhThucThanhToan hinhThucThanhToan;
//    @OneToMany(mappedBy = "donHang", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<ThanhToan> danhSachThanhToan;
    @ManyToOne(cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH
    })
    @JoinColumn(name = "ma_hinh_thuc_giao_hang")
    private HinhThucGiaoHang hinhThucGiaoHang;
}