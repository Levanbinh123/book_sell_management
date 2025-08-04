package com.example.book_sell_management.service;
import com.example.book_sell_management.dto.donHangDto.CreateDonHangDto;
import com.example.book_sell_management.dto.donHangDto.UpdateDonHang;
import com.example.book_sell_management.entity.DonHang;
import com.example.book_sell_management.entity.NguoiDung;
import org.springframework.http.ResponseEntity;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface DonHangService {
    // Tạo đơn hàng mới
    ResponseEntity<DonHang> createDonHang(CreateDonHangDto createDonHangDto,
                                          String tenHinhThucThanhToan,
                                          String tenHinhThucGiaoHang,
                                          NguoiDung nguoiDung);
    public ResponseEntity<?> xuLyDonHangMoMo(Integer maDonHang,String maThanhToan) throws Exception;
    public ResponseEntity<?> xuLyDonHangCod(Integer maDonHang) throws Exception;
    // Cập nhật đơn hàng (ví dụ thay đổi trạng thái, địa chỉ...)
    DonHang updateDonHang(Integer maSach, String tenDangNhap, UpdateDonHang updateDonHang) throws Exception;
    void deleteDonHangById(int id);
    public ResponseEntity<Void> trangThanhToan(String tenDangNhap,String maThanhToan);
    public ResponseEntity<Void> huyDonHang(Integer maDonHang );
    // Lấy danh sách tất cả đơn hàng
    List<DonHang> getAllDonHang();
    public ResponseEntity<Void> xacNhanKhachHangNhanHangThanhCong(Integer maDonHang);
    // Tìm đơn hàng theo ID
    Optional<DonHang> getDonHangById(int id);

    // Tìm đơn hàng theo người dùng
    List<DonHang> getDonHangByNguoiDungId(int nguoiDungId);

    // Tìm đơn hàng theo trạng thái
    List<DonHang> getDonHangByTrangThai(String trangThai);

    // Cập nhật trạng thái đơn hàng
    ResponseEntity<DonHang> updateTrangThaiDonHang(Integer maDonHang) throws Exception;

    // Lọc đơn theo khoảng thời gian
    List<DonHang> getDonHangByDateRange(Date fromDate, Date toDate) throws Exception;

    // Thống kê tổng doanh thu trong một ngày
    public List<DonHang> getDoanhThuTheoNgay(Date ngay);
    public Double tinhTongDoanhThuTheoNgay(Date ngay);
}
