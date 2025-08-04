package com.example.book_sell_management.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "quyen")
public class Quyen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_quyen")
    private int maQuyen;
    @Column(name = "ten_quyen")
    private String tenQuyen;

    public Quyen(String tenQuyen) {
        this.tenQuyen = tenQuyen;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH
    })
    @JoinTable(
            name = "nguoidung_quyen",
            joinColumns = @JoinColumn(name = "ma_quyen"),
            inverseJoinColumns = @JoinColumn(name = "ma_nguoi_dung")
    )
    @JsonIgnore
    List<NguoiDung> danhSachNguoiDung;
}
