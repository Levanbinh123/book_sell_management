package com.example.book_sell_management.dto.donHangDto;

import com.example.book_sell_management.entity.DonHang;
import com.example.book_sell_management.entity.HinhThucGiaoHang;
import com.example.book_sell_management.entity.HinhThucThanhToan;
import jakarta.persistence.Column;
import lombok.Data;

import java.sql.Date;
@Data
public class UpdateDonHang {
    private Date ngayTao;
    private String diaChiMuaHang;
    private String diaChiNhanHang;
    private Integer soLuong;
    private String phuongThucGiaoHang;
    private String phuongThucThanhToan;

}
