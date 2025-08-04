//package com.example.book_sell_management.utils;
//
//import com.example.book_sell_management.entity.NguoiDung;
//import com.example.book_sell_management.entity.Quyen;
//import com.example.book_sell_management.repository.NguoiDungRepository;
//import com.example.book_sell_management.repository.QuyenRepository;
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//public class DataInitializer implements CommandLineRunner {
//    @Autowired
//    private QuyenRepository quyenRepository;
//
//    @Autowired
//    private NguoiDungRepository nguoiDungRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//    @Override
//    public void run(String ... args) {
//        if (!quyenRepository.existsByTenQuyen(("ROLE_USER"))) {
//            quyenRepository.save(new Quyen("ROLE_USER"));
//        }
//        if (!quyenRepository.existsByTenQuyen("ROLE_ADMIN")) {
//            quyenRepository.save(new Quyen("ROLE_ADMIN"));
//        }
//        if (!quyenRepository.existsByTenQuyen("ROLE_STAFF")) {
//            quyenRepository.save(new Quyen("ROLE_STAFF"));
//        }
//        if(!nguoiDungRepository.existsByTenDangNhap("admin")){
//            NguoiDung admin=new NguoiDung();
//            admin.setTenDangNhap("admin");
//            admin.setEmail("admin@example.com");
//            admin.setMatKhau(passwordEncoder.encode("admin123"));
//            admin.setHoDem("Super");
//            admin.setTen("Admin");
//            admin.setDaKichHoat(true);
//            admin.setMaKichHoat(null);
//            Quyen adminRole = quyenRepository.findByTenQuyen("ROLE_ADMIN");
//            admin.setDanhSachQuyen(List.of(adminRole));
//            nguoiDungRepository.save(admin);
//        }
//    }
//}
