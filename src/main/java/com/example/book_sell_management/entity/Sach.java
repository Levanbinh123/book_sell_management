package com.example.book_sell_management.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
@Entity
@Data
@Table(name = "sach")
public class Sach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_sach")
    private int maSach;

    @Column(name = "ten_sach", length = 256)
    private String tenSach;

    @Column(name = "ten_tac_gia", length = 512)
    private String tenTacGia;

    @Column(name = "isbn", length = 256)
    private String ISBN;

    @Column(name = "mo_ta", columnDefinition = "text")
    private String moTa;

    @Column(name="gia_niem_yet")
    private Double giaNiemYet;

    @Column(name="gia_ban")
    private Double giaBan;

    @Column(name="so_luong")
    private Integer soLuong;

    @Column(name="trung_binh_xep_hang")
    private Double trungBinhXepHang;

    @Column(name = "so_luong_da_ban")
    private Integer soLuongDaBan;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH
    })
    @JoinTable(
            name = "sach_theloai",
            joinColumns = @JoinColumn(name = "ma_sach"),
            inverseJoinColumns = @JoinColumn(name = "ma_the_loai")
    )
    List<TheLoai> danhSachTheLoai;

    @OneToMany(mappedBy = "sach", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<HinhAnh> danhSachHinhAnh;

    @OneToMany(mappedBy = "sach", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<SuDanhGia> danhSachSuDanhGia;

    @OneToMany(mappedBy = "sach", fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH
    }) @JsonIgnore//khong load cai nay
    List<ChiTietDonHang> danhSachChiTietDonHang;

    @OneToMany( mappedBy = "sach", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<SachYeuThich> danhSachSachYeuThich;
}