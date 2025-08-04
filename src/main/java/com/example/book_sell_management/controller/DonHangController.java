package com.example.book_sell_management.controller;
import com.example.book_sell_management.dto.donHangDto.CreateDonHangDto;
import com.example.book_sell_management.dto.userDto.NguoiDungDTO;
import com.example.book_sell_management.entity.DonHang;
import com.example.book_sell_management.entity.NguoiDung;
import com.example.book_sell_management.request.ThongBao;
import com.example.book_sell_management.service.DonHangService;
import com.example.book_sell_management.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
@Tag(name = "04 - Quản lý đơn hàng")
@RestController
@RequestMapping("/api/donHang")
public class DonHangController {
    @Autowired
    private DonHangService donHangService;
    @Autowired
    private UserService userService;
    @PostMapping("/hinhThucThanhToan/sach")//loginuser
    public ResponseEntity<DonHang> createDonHang(@RequestBody CreateDonHangDto createDonHangDto,
                                                 @RequestParam String tenHinhThucThanhToan,
                                                 @RequestParam String tenHinhThucGiaoHang,
                                                 @RequestHeader("Authorization") String token
    ) throws Exception {
        ResponseEntity<NguoiDungDTO> nguoiDungTenDangNhap=userService.findUserByJwt(token);
        NguoiDung nguoiDung1=userService.findByTenDangNhap(nguoiDungTenDangNhap.getBody().getTenDangNhap());
        if(nguoiDung1==null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
       ResponseEntity<DonHang> donHang= donHangService.createDonHang(createDonHangDto,tenHinhThucThanhToan,tenHinhThucGiaoHang,nguoiDung1);

        if(donHang.getStatusCode()==HttpStatus.CREATED) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PutMapping("/xacnhan/{maDonHang}")//staff
    public ResponseEntity<?> updateTrangThaiDonHang(@PathVariable Integer maDonHang) throws Exception {
        ResponseEntity<DonHang> donHang=donHangService.updateTrangThaiDonHang(maDonHang);
        if(donHang.getStatusCode()==HttpStatus.NOT_FOUND) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else if(donHang.getStatusCode()==HttpStatus.INTERNAL_SERVER_ERROR) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new ThongBao("da xac nhan don hang thanh cong"), HttpStatus.OK);
    }
    @PutMapping("/xulygiaohang-momo/{maDonHang}")//staff
    public ResponseEntity<?> xuLyDonHangMoMo(@PathVariable Integer maDonHang,@RequestParam String maThanhToan) throws Exception {
       ResponseEntity<?> donHang=donHangService.xuLyDonHangMoMo(maDonHang,maThanhToan);
       if (donHang.getStatusCode()==HttpStatus.NOT_FOUND) {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else if(donHang.getStatusCode()==HttpStatus.INTERNAL_SERVER_ERROR){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

       return new ResponseEntity<>("successed", HttpStatus.OK);

    }
    @PutMapping("/xulygiaohang-cod/{maDonHang}")//staff
    public ResponseEntity<?> xuLyDonHangCod(@PathVariable Integer maDonHang) throws Exception {
        ResponseEntity<?> donhang=donHangService.xuLyDonHangCod(maDonHang);
        if(donhang.getStatusCode()==HttpStatus.NOT_FOUND) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(donhang.getStatusCode()==HttpStatus.BAD_REQUEST) {

            return new ResponseEntity<>("xem lai dieu kien", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("xu ly don hang thanh cong cap nhat trang thai DANGGIAO", HttpStatus.OK);

    }
    @PutMapping("/hoantatdonhang/{maDonHang}")//staff
    public ResponseEntity<Void> xacNhanKhachHangNhanHangThanhCong(@PathVariable Integer maDonHang){
       ResponseEntity<?> xac_nhan_don_hang= donHangService.xacNhanKhachHangNhanHangThanhCong(maDonHang);
       if(xac_nhan_don_hang.getStatusCode().equals(HttpStatus.NOT_FOUND)){
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
            return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/huydonhang/{maDonHang}")//loginuser
    public ResponseEntity<Void> huyDonHang(@PathVariable Integer maDonHang ){
       ResponseEntity<?> huyDonHang= donHangService.huyDonHang(maDonHang);
       if(huyDonHang.getStatusCode().equals(HttpStatus.OK)){
           return new ResponseEntity<>(HttpStatus.OK);
       }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/admin/{id}")//admin
    public ResponseEntity<Void> deleteDonHangById(@PathVariable int id){
        donHangService.deleteDonHangById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/admin/doanhthu")//admin
    public ResponseEntity<Double> getDoanhThuNgay(@RequestParam("ngay") @DateTimeFormat(pattern = "yyyy-MM-dd") Date ngay) {
        Double tongDoanhThu = donHangService.tinhTongDoanhThuTheoNgay(ngay);
        return ResponseEntity.ok(tongDoanhThu);
    }
    @GetMapping("/admin")/// admin
    public List<DonHang> getAllDonHang(){
        return donHangService.getAllDonHang();
    }
}
