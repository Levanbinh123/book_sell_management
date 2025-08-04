package com.example.book_sell_management.service.impl;

import com.example.book_sell_management.dto.donHangDto.CreateDonHangDto;
import com.example.book_sell_management.dto.donHangDto.UpdateDonHang;
import com.example.book_sell_management.entity.*;
import com.example.book_sell_management.enumm.TrangThaDonHang;
import com.example.book_sell_management.enumm.TrangThaiThanhToan;
import com.example.book_sell_management.repository.*;
import com.example.book_sell_management.request.ThongBao;
import com.example.book_sell_management.service.DonHangService;
import com.example.book_sell_management.service.EmailService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class DonHangServiceImpl implements DonHangService {
    @Autowired
    private DonHangRepository donHangRepository;
    @Autowired
    private ChiTietDonHangRepository chiTietDonHangRepository;
    @Autowired
    private HinhThucGiaoHangRepository hinhThucGiaoHangRepository;
    @Autowired
    private SachRepository sachRepository;
    @Autowired
    private HinhThucThanhToanRepository hinhThucThanhToanRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Override//nguoi dung tao moi don hang /database cap nhat chi tiet don hang- cap nhat lai sach trong kho va da ban.
    public ResponseEntity<DonHang> createDonHang(CreateDonHangDto createDonHangDto, String tenHinhThucThanhToan, String tenHinhThucGiaoHang, NguoiDung nguoiDung) {
        DonHang donHang = new DonHang();
        Optional<HinhThucGiaoHang> hinhThucGiaoHang= Optional.ofNullable(hinhThucGiaoHangRepository.findByTenHinhThucGiaoHang(tenHinhThucGiaoHang));
        HinhThucGiaoHang hinhThucGiaoHang1=hinhThucGiaoHang.get() ;
        if (hinhThucGiaoHang == null) {
            throw new RuntimeException("Không tìm thấy hình thức giao hàng: " + tenHinhThucGiaoHang);
        }
       else {
            donHang.setChiPhiGiaoHang(hinhThucGiaoHang1.getChiPhiGiaoHang());
        }
        donHang.setHinhThucGiaoHang(hinhThucGiaoHang1);
        HinhThucThanhToan hinhThucThanhToan=hinhThucThanhToanRepository.findByTenHinhThucThanhToan(tenHinhThucThanhToan);
        donHang.setHinhThucThanhToan(hinhThucThanhToan);
        donHang.setDiaChiMuaHang(createDonHangDto.getDiaChiMuaHang());
        donHang.setNguoiDung(nguoiDung);
        donHang.setDiaChiNhanHang(createDonHangDto.getDiaChiNhanHang());
        donHang.setNgayTao(new java.sql.Date(System.currentTimeMillis()));
        donHang.setTrangThaiThanhToan(String.valueOf(TrangThaiThanhToan.CHUA_THANH_TOAN));
        donHang.setTrangThai(String.valueOf(TrangThaDonHang.CHO_XAC_NHAN));
        List<ChiTietDonHang> chiTietList = new ArrayList<>();
        double tongTienSanPham = 0.0;
        int tongSoLuong = 0;
        for(Sach item: createDonHangDto.getDanhSachSanPham()){
            Optional<Sach> sach1=sachRepository.findById(item.getMaSach()) ;
            if(!sach1.isPresent())
            {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            if (item.getSoLuong() <= 0) {
                throw new IllegalArgumentException("Số lượng phải lớn hơn 0");
            }

            Sach sach = sach1.get();
            if (sach.getSoLuong() < item.getSoLuong()) {
                throw new IllegalArgumentException("Sách mã " + sach.getMaSach() + " không đủ số lượng tồn kho");
            }
            sach.setSoLuong(sach.getSoLuong()-item.getSoLuong());
            sach.setSoLuongDaBan(sach.getSoLuongDaBan()+item.getSoLuong());
            sachRepository.save(sach);
            ChiTietDonHang chiTietDonHang=new ChiTietDonHang();
            chiTietDonHang.setDonHang(donHang);
            chiTietDonHang.setSach(sach);
            chiTietDonHang.setSoLuong(item.getSoLuong());
            chiTietDonHang.setGiaBan(sach.getGiaBan());
            chiTietList.add(chiTietDonHang);
            tongTienSanPham+=item.getSoLuong()*sach.getGiaBan();
            tongSoLuong+=item.getSoLuong();
        }
        donHang.setDanhSachChiTietDonHang(chiTietList);
        donHang.setSoLuong(tongSoLuong);
        donHang.setTongTienSanPham(tongTienSanPham);
        double tongTien = tongTienSanPham + donHang.getChiPhiGiaoHang();
        donHang.setTongTien(tongTien);
        DonHang savedonHang=donHangRepository.save(donHang);
        return new ResponseEntity<>(savedonHang, HttpStatus.CREATED);
    }
    @Override//cap nhat trang thai huy va tra ve sach lai trong kho
    @Transactional
    public ResponseEntity<Void> huyDonHang(Integer maDonHang ){
        // Kiểm tra đơn hàng có tồn tại không
        DonHang huyDonHang = donHangRepository.findById(maDonHang)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy đơn hàng"));
        if(!huyDonHang.getTrangThai().equals(String.valueOf(TrangThaDonHang.HOAN_TAT))){
        huyDonHang.setTrangThai(String.valueOf(TrangThaDonHang.HUY));
        List<ChiTietDonHang> chiTietDonHangs=chiTietDonHangRepository.findByDonHang_MaDonHang(huyDonHang.getMaDonHang());
        for(ChiTietDonHang chiTietDonHang1 : chiTietDonHangs){
            Sach sach=chiTietDonHang1.getSach();
            if(sach==null){continue;}
            sach.setSoLuong(sach.getSoLuong()+chiTietDonHang1.getSoLuong());
            sach.setSoLuongDaBan(sach.getSoLuongDaBan()-chiTietDonHang1.getSoLuong());
            sachRepository.save(sach);
        }}
        donHangRepository.save(huyDonHang);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Override
    @Transactional//xu ly don hang thanh toan bang ship-cod-xac nhan- chuyen sang cho van chuyen
    public ResponseEntity<?> xuLyDonHangCod(Integer maDonHang) throws Exception {

        Optional<DonHang> optionalDonHang = donHangRepository.findById(maDonHang);
        if (!optionalDonHang.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        DonHang donHang = optionalDonHang.get();
        try {
            if (donHang.getTrangThai().equals(String.valueOf(TrangThaDonHang.DA_XAC_NHAN))) {
                //thanh toan momo
                if (donHang.getHinhThucThanhToan().getMaHinhThucThanhToan() == 1) {
                    donHang.setMaThanhToan(null);
                    donHang.setTrangThaiThanhToan(String.valueOf(TrangThaiThanhToan.DA_THANH_TOAN));
                    donHang.setTrangThai(String.valueOf(TrangThaDonHang.DANG_GIAO));
                    donHangRepository.save(donHang);
                }
                return new ResponseEntity<>(new ThongBao("da xu ly don hang- dang giao cho ben van chuyen"), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    }
    @Override
    @Transactional
    //kiem tra thanh toan va van chuyen don hang theo  phuong thuc  momo
    public ResponseEntity<?> xuLyDonHangMoMo(Integer maDonHang,String maThanhToan) throws Exception {
        Optional<DonHang> optionalDonHang = donHangRepository.findById(maDonHang);
        if (!optionalDonHang.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        DonHang donHang = optionalDonHang.get();
        if(donHang.getTrangThai().equals(TrangThaDonHang.DA_XAC_NHAN.name())){
            //thanh toan momo
            try {
                if(donHang.getHinhThucThanhToan().getMaHinhThucThanhToan()==2) {
                    if(donHang.getMaThanhToan().equals(maThanhToan)) {
                        donHang.setMaThanhToan(null);
                        donHang.setTrangThaiThanhToan(String.valueOf(TrangThaiThanhToan.DA_THANH_TOAN));
                        donHang.setTrangThai(String.valueOf(TrangThaDonHang.DANG_GIAO));
                        donHangRepository.save(donHang);
                    }
                    return new ResponseEntity<>("da hoan tat thanh toan dang giao", HttpStatus.OK);
                }
            }catch (Exception e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
        return new ResponseEntity<>("don hang chua duoc xac nhan", HttpStatus.BAD_REQUEST);
    }
    @Override//xac nhan hoan tat don hang sau khi giao xong va nhan tien
    public ResponseEntity<Void> xacNhanKhachHangNhanHangThanhCong(Integer maDonHang){
        DonHang donHang=donHangRepository.findById(maDonHang).get();
        if(donHang!=null){
            if(donHang.getHinhThucThanhToan().getTenHinhThucThanhToan().equals("THANH_TOAN_TRUC_TIEP")) {
                if(donHang.getTrangThai().equals(String.valueOf(TrangThaDonHang.DANG_GIAO))) {}
                //shiper nhan tien xong nhan nut xac nhan tren he thong
                donHang.setTrangThaiThanhToan(String.valueOf(TrangThaiThanhToan.DA_THANH_TOAN));
                donHang.setTrangThai(String.valueOf(TrangThaDonHang.HOAN_TAT));
                donHangRepository.save(donHang);
            }
            //don hang momo
           else if (donHang.getHinhThucThanhToan().getTenHinhThucThanhToan().equals(String.valueOf(com.example.book_sell_management.enumm.HinhThucThanhToan.MOMO))) {
                donHang.setTrangThai(String.valueOf(TrangThaDonHang.HOAN_TAT));
                donHangRepository.save(donHang);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        //khach hang khong nhan duoc hang hoac co su co khong giao duoc


    }
    @Override//nguoi dung momo -nhap ma thanh toan
    public ResponseEntity<Void> trangThanhToan(String tenDangNhap,String maThanhToan) {
        DonHang donHang=donHangRepository.findByTenDangNhap(tenDangNhap);
        if(maThanhToan.equals(donHang.getMaThanhToan())){
            donHang.setTrangThaiThanhToan(String.valueOf(TrangThaiThanhToan.DA_THANH_TOAN));
        }
        donHangRepository.save(donHang);
        return null;
    }
    private String taoMaThanhToan(){
        // Tạo mã ngẫu nhiên
        return UUID.randomUUID().toString();
    }
    private void guiEmailXacNhanThanhToan(String email, String maKichHoat){
        String subject = "xac nhan don hang của bạn tại WebBanSach";
        String text = "Vui lòng sử dụng mã sau để thanh toan <"+email+">:<html><body><br/><h1>"+maKichHoat+"</h1></body></html>";
        emailService.sendMessage("tunletest1.email@gmail.com", email, subject, text);
    }
    @Override//nguoi dung dieu chinh don hang cua minh
    public DonHang updateDonHang(Integer maSach, String tenDangNhap, UpdateDonHang updateDonHang) throws Exception {
        return null;
    }

    @Override
    @Transactional//admin xac nhan don hang da hhuy va xoa khoi danh sach
    public void deleteDonHangById(int id) {
        // Tìm đơn hàng theo id, nếu không có thì ném lỗi
        DonHang donHang = donHangRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy đơn hàng"));
        // Kiểm tra trạng thái HỦY mới được xóat
        if (TrangThaDonHang.HUY.name().equals(donHang.getTrangThai())) {
            // Xóa chi tiết đơn hàng trước
            List<ChiTietDonHang> chiTietDonHangs = chiTietDonHangRepository.findByDonHang_MaDonHang(id);
            chiTietDonHangRepository.deleteAll(chiTietDonHangs);
            // Xóa đơn hàng
            donHangRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Chỉ được xóa đơn hàng đã hủy");
        }
    }
    @Override//admin lay tat ca don hang
    public List<DonHang> getAllDonHang() {
        return donHangRepository.findAll();
    }

    @Override//admin kiem truy van don hang theo id
    public Optional<DonHang> getDonHangById(int id) {
        return donHangRepository.findById(id);
    }

    @Override//nguoi dung xem cac don hang cua minh
    public List<DonHang> getDonHangByNguoiDungId(int nguoiDungId) {

        List<DonHang> donHang= (List<DonHang>) donHangRepository.findByMaNguoiDung(nguoiDungId);
        return donHang;
    }

    @Override
    public List<DonHang> getDonHangByTrangThai(String trangThai) {
        return donHangRepository.getDonHangByTrangThai(trangThai);
    }

    @Override//admin xac nhan don hang va neu don hang thanh toan bang momo thi gui ma thanh toan
    public ResponseEntity<DonHang> updateTrangThaiDonHang(Integer maDonHang) throws Exception {
        DonHang donHang=donHangRepository.findById( maDonHang).get();
        if(donHang==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            if(donHang.getTrangThai().equals("CHUA_XAC_NHAN")) {
                donHang.setTrangThai(String.valueOf(TrangThaDonHang.DA_XAC_NHAN));
            }
            if (donHang.getHinhThucThanhToan() != null && donHang.getHinhThucThanhToan().getMaHinhThucThanhToan()==2) {
                String maThanhToan = UUID.randomUUID().toString();
                donHang.setMaThanhToan(maThanhToan);
                String email = donHang.getNguoiDung() != null ? donHang.getNguoiDung().getEmail() : null;
                if (email != null && !email.isEmpty()) {
                    guiEmailXacNhanThanhToan(email, donHang.getMaThanhToan());
                } else {
                    throw new Exception("Email người dùng không tồn tại hoặc trống!");
                }
            }
            donHangRepository.save(donHang);
            return new ResponseEntity<>(donHang, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Override//lay don hang theo ngay
    public List<DonHang> getDonHangByDateRange(Date fromDate, Date toDate) throws Exception {
        List<DonHang> donHangs= donHangRepository.getDonHangByDateRange(fromDate, toDate);
        if(donHangs==null){
            throw new Exception("don hang is not found");
        }
        return donHangs;
    }
    @Override//tinh doanh thu theo ngay
    public List<DonHang> getDoanhThuTheoNgay(Date ngay) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(ngay);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startOfDay = calendar.getTime();
        // Set 23:59:59
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date endOfDay = calendar.getTime();
        return donHangRepository.getDoanhThuTheoNgay(startOfDay, endOfDay);
    }
    @Override//tinh tong
    public Double tinhTongDoanhThuTheoNgay(Date ngay) {
        List<DonHang> ds=getDoanhThuTheoNgay(ngay);
        return ds.stream().mapToDouble(DonHang::getTongTien).sum();
    }
}
