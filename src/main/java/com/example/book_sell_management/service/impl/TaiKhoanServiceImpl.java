package com.example.book_sell_management.service.impl;

import com.example.book_sell_management.dto.userDto.*;
import com.example.book_sell_management.entity.NguoiDung;
import com.example.book_sell_management.repository.NguoiDungRepository;
import com.example.book_sell_management.request.ThongBao;
import com.example.book_sell_management.security.JwtService;
import com.example.book_sell_management.service.EmailService;
import com.example.book_sell_management.service.TaiKhoanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import java.util.UUID;

@Service
public class TaiKhoanServiceImpl implements TaiKhoanService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private EmailService emailService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private NguoiDungRepository nguoiDungRepository;
    @Override
    public ResponseEntity<?> dangKyNguoiDung(@Valid DangKyRequest dangKyRequest) {
        if(nguoiDungRepository.existsByTenDangNhap(dangKyRequest.getTenDangNhap())){
            return ResponseEntity.badRequest().body(new ThongBao("ten dang nhap da ton tai"));
        }
        if(nguoiDungRepository.existsByEmail(dangKyRequest.getEmail())){
            return ResponseEntity.badRequest().body(new ThongBao("email da ton tai"));
        }
        String encryptPassword = bCryptPasswordEncoder.encode(dangKyRequest.getMatKhau());
        NguoiDung nguoiDungdk =new NguoiDung();
        nguoiDungdk.setTenDangNhap(dangKyRequest.getTenDangNhap());
        nguoiDungdk.setEmail(dangKyRequest.getEmail());
        nguoiDungdk.setHoDem(dangKyRequest.getHoDem());
        nguoiDungdk.setTen(dangKyRequest.getTen());
        nguoiDungdk.setMatKhau(encryptPassword);
        nguoiDungdk.setGioiTinh(dangKyRequest.getGioiTinh());
        nguoiDungdk.setSoDienThoai(dangKyRequest.getSoDienThoai());
        // Gán và gửi thông tin kích hoạt
        nguoiDungdk.setMaKichHoat(taoMaKichHoat());
        nguoiDungdk.setDaKichHoat(false);
        nguoiDungRepository.save(nguoiDungdk);
        // Gửi email cho người dùng để họ kích hoạt
        guiEmailKichHoat(nguoiDungdk.getEmail(), nguoiDungdk.getMaKichHoat());

        return ResponseEntity.ok().body(nguoiDungdk);
    }


    @Override
    public ResponseEntity<?> kichHoatTaiKhoan(KichHoatRequest kichHoatRequest) {
        NguoiDung nguoiDung = nguoiDungRepository.findByEmail(kichHoatRequest.getEmail());

        if (nguoiDung == null) {
            return ResponseEntity.badRequest().body(new ThongBao("Người dùng không tồn tại!"));
        }

        if (nguoiDung.isDaKichHoat()) {
            return ResponseEntity.badRequest().body(new ThongBao("Tài khoản đã được kích hoạt!"));
        }

        if (kichHoatRequest.getMaKichHoat().equals(nguoiDung.getMaKichHoat())) {
            nguoiDung.setDaKichHoat(true);
            nguoiDung.setMaKichHoat(null);
            nguoiDungRepository.save(nguoiDung);
            return ResponseEntity.ok("Kích hoạt tài khoản thành công!");
        } else {
            return ResponseEntity.badRequest().body(new ThongBao("Mã kích hoạt không chính xác!"));
        }
    }
    private String taoMaKichHoat(){
        // Tạo mã ngẫu nhiên
        return UUID.randomUUID().toString();
    }

    private void guiEmailKichHoat(String email, String maKichHoat){
        String subject = "Kích hoạt tài khoản của bạn tại WebBanSach";
        String text = "Vui lòng sử dụng mã sau để kich hoạt cho tài khoản <"+email+">:<html><body><br/><h1>"+maKichHoat+"</h1></body></html>";
        text+="<br/> Click vào đường link để kích hoạt tài khoản: ";
        String url = "http://localhost:3000/kich-hoat/"+email+"/"+maKichHoat;
        text+=("<br/> <a href="+url+">"+url+"</a> ");

        emailService.sendMessage("tunletest1.email@gmail.com", email, subject, text);
    }
    @Override
    public ResponseEntity<?> dangNhap(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            // Nếu xác thực thành công, tạo token JWT
            if(authentication.isAuthenticated()){
                final String jwt = jwtService.generateToken(loginRequest.getUsername());
                return ResponseEntity.ok(new JwtResponse(jwt));
            }
        }catch (AuthenticationException e){
            // Xác thực không thành công, trả về lỗi hoặc thông báo
            return ResponseEntity.badRequest().body("Tên đăng nhập hặc mật khẩu không chính xác.");
        }
        return ResponseEntity.badRequest().body("Xác thực không thành công.");
    }


    public ResponseEntity<?> resetPassword(ResetPasswordRequest resetPasswordRequest) {
        NguoiDung nguoiDung = nguoiDungRepository.findByEmail(resetPasswordRequest.getEmail());

        if (nguoiDung == null) {
            return ResponseEntity
                    .badRequest()
                    .body("Email không tồn tại.");
        }

        // Kiểm tra mật khẩu cũ có khớp không
        if (!bCryptPasswordEncoder.matches(resetPasswordRequest.getOldPassword(), nguoiDung.getMatKhau())) {
            return ResponseEntity
                    .badRequest()
                    .body("Mật khẩu cũ không chính xác.");
        }

        // Cập nhật mật khẩu mới đã mã hóa
        String newEncodedPassword = bCryptPasswordEncoder.encode(resetPasswordRequest.getNewPassWord());
        nguoiDung.setMatKhau(newEncodedPassword);
        nguoiDungRepository.save(nguoiDung);

        return ResponseEntity.ok("Đổi mật khẩu thành công.");
    }

    @Override
    public ResponseEntity<?> forgetRequest(KichHoatRequest kichHoatRequest) {
        NguoiDung kiemTraEmailNguoiDung = nguoiDungRepository.findByEmail(kichHoatRequest.getEmail());
        if (kiemTraEmailNguoiDung == null) {
            return  ResponseEntity.notFound().build();
        }
        kiemTraEmailNguoiDung.setMaKichHoat(taoMaKichHoat());
        guiEmailKichHoat(kiemTraEmailNguoiDung.getEmail(),kiemTraEmailNguoiDung.getMaKichHoat());
        nguoiDungRepository.save(kiemTraEmailNguoiDung);
       return ResponseEntity.ok("da gui ma lay lai mat khau");
    }

    @Override
    public ResponseEntity<?> forgetPassword(ForgetPasswordRequest forgetPasswordRequest) {
        NguoiDung kiemTraEmailNguoiDung = nguoiDungRepository.findByEmail(forgetPasswordRequest.getEmail());
        if (kiemTraEmailNguoiDung == null) {
            return  ResponseEntity.notFound().build();
        }
       if(kiemTraEmailNguoiDung.getMaKichHoat().equals(forgetPasswordRequest.getMaKichHoat())){
           kiemTraEmailNguoiDung.setMaKichHoat(null);
           kiemTraEmailNguoiDung.setMatKhau(bCryptPasswordEncoder.encode(forgetPasswordRequest.getNewPassword()));
           nguoiDungRepository.save(kiemTraEmailNguoiDung);
           return ResponseEntity.ok("da thay doi mat khau thanh cong");
       }return ResponseEntity.badRequest().build();
    }

    private void guiEmailLayLaiMatKhau(String email, String maKichHoat){
        String subject = "khoi phuc tài khoản của bạn tại WebBanSach";
        String text = "Vui lòng sử dụng mã sau để lay lai cho tài khoản <"+email+">:<html><body><br/><h1>"+maKichHoat+"</h1></body></html>";
        text+="<br/> Click vào đường link để kích hoạt tài khoản: ";
        String url = "http://localhost:3000/kich-hoat/"+email+"/"+maKichHoat;
        text+=("<br/> <a href="+url+">"+url+"</a> ");

        emailService.sendMessage("d.email@gmail.com", email, subject, text);
    }
}

