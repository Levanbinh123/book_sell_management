package com.example.book_sell_management.dto.donHangDto;

import com.example.book_sell_management.entity.Sach;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class CreateDonHangDto {
    private Date ngayTao;
    private String diaChiMuaHang;
    private String diaChiNhanHang;
    private Integer soLuong;
    private List<Sach> danhSachSanPham;
    // VD: "CHO_XAC_NHAN", "DANG_GIAO", "DA_GIAO", "HUY"

}
