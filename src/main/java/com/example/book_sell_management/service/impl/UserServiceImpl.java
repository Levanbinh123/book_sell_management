package com.example.book_sell_management.service.impl;

import com.example.book_sell_management.dto.userDto.NguoiDungDTO;
import com.example.book_sell_management.dto.userDto.UpdateUser;
import com.example.book_sell_management.entity.NguoiDung;
import com.example.book_sell_management.entity.Quyen;
import com.example.book_sell_management.repository.NguoiDungRepository;
import com.example.book_sell_management.repository.QuyenRepository;
import com.example.book_sell_management.security.JwtService;
import com.example.book_sell_management.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    public NguoiDungRepository nguoiDungRepository;
    public QuyenRepository quyenRepository;
@Autowired
    public UserServiceImpl(NguoiDungRepository nguoiDungRepository, QuyenRepository quyenRepository) {
        this.nguoiDungRepository = nguoiDungRepository;
        this.quyenRepository = quyenRepository;
    }

    @Override
    public NguoiDung findByTenDangNhap(String tenDangNhap) throws Exception {
        NguoiDung nguoiDung= nguoiDungRepository.findByTenDangNhap(tenDangNhap);
        if(nguoiDung==null){
            throw new Exception("not found user");
        }
        return nguoiDung;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        NguoiDung nguoiDung=nguoiDungRepository.findByTenDangNhap(username);
        if(nguoiDung==null){
            throw new UsernameNotFoundException(username);
        }
        return new User(nguoiDung.getTenDangNhap(), nguoiDung.getMatKhau(),rolesToAuthorities(nguoiDung.getDanhSachQuyen()));
    }
    private Collection<? extends GrantedAuthority> rolesToAuthorities(Collection<Quyen> quyens) {
        return quyens.stream().map(quyen -> new SimpleGrantedAuthority(quyen.getTenQuyen())).collect(Collectors.toList());
    }
    @Override
    public void deleteUserById(Integer id) throws Exception {
    Optional<NguoiDung> kiemTraNguoiDung= nguoiDungRepository.findById(id);
    if(kiemTraNguoiDung.isEmpty()){
        throw new Exception("not found user");
    }
        nguoiDungRepository.deleteById(id);
    }
    @Override
    public ResponseEntity<NguoiDung> themNguoiDung(NguoiDung nguoiDung) throws Exception {
        try {
            if(nguoiDungRepository.existsByTenDangNhap(nguoiDung.getTenDangNhap())){
                throw new Exception("exists user");
            }

            NguoiDung savedUser = nguoiDungRepository.save(nguoiDung);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @Override
    public List<NguoiDung> findAllNguoiDung() {
       List<NguoiDung> nguoiDungs= nguoiDungRepository.findAll();
       if(nguoiDungs==null){
           return null;
       }
       return nguoiDungs;
    }
    @Override
    public NguoiDung updateNguoiDung(UpdateUser updateUser,int id) throws Exception {
            Optional<NguoiDung> checkUser=nguoiDungRepository.findById(id);
            if(checkUser.isEmpty()){
                return null;
                }
            checkUser.get().setTen(updateUser.getTen());
            checkUser.get().setTenDangNhap(updateUser.getTenDangNhap());
            checkUser.get().setMatKhau(updateUser.getMatKhau());
            checkUser.get().setGioiTinh(updateUser.getGioiTinh());
                NguoiDung savedNguooiDung= nguoiDungRepository.save(checkUser.get());
                return savedNguooiDung;
    }

    @Override
    public ResponseEntity<NguoiDungDTO> findUserByJwt(String jwt) throws Exception {
        String tenDangNhap = JwtService.getTenDangNhapFromToken(jwt);
        if(tenDangNhap==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Optional<NguoiDung> nguoiDung = Optional.ofNullable(nguoiDungRepository.findByTenDangNhap(tenDangNhap));
        if (nguoiDung == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        try {
            NguoiDungDTO dto = new NguoiDungDTO();
            dto.setTenDangNhap(nguoiDung.get().getTenDangNhap());
            dto.setEmail(nguoiDung.get().getEmail());
            dto.setDanhSachQuyen(nguoiDung.get().getDanhSachQuyen().stream()
                    .map(Quyen::getTenQuyen)
                    .collect(Collectors.toList()));
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }}
