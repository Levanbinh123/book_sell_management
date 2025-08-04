//package com.example.book_sell_management.entity;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//import java.util.Date;
//
//@Entity
//@Table(name = "thanh_toan")
//@Data
//public class ThanhToan {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "ma_thanh_toan")
//    private Integer maThanhToan;
//    @Column(name = "loai_thanh_toan")
//    private String loaiThanhToan; // MOMO, COD
//    @Column(name = "trang_thai")
//    private String trangThai; // CHO_THANH_TOAN, DA_THANH_TOAN, THAT_BAI
//    @Column(name = "so_tien")
//    private Double soTien;
//    @Column(name = "pay_url", length = 1000)
//    private String payUrl;
//    @ManyToOne
//    @JoinColumn(name = "ma_don_hang")
//    private DonHang donHang;
//    @Column(name = "ngay_thanh_toan")
//    private Date ngayThanhToan;
//}