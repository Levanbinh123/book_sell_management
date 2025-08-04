package com.example.book_sell_management.controller;

import com.example.book_sell_management.entity.HinhAnh;
import com.example.book_sell_management.entity.Sach;
import com.example.book_sell_management.service.HinhAnhService;
import com.example.book_sell_management.service.SachService;
import com.example.book_sell_management.service.impl.FileStorageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Tag(name = "05 - Quản lý hình ảnh")
@RestController
@RequestMapping("/api/hinhanh")
public class HinhAnhController {
    @Autowired
    private HinhAnhService hinhAnhService;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private SachService sachService;
    @PostMapping("/upload")//loginuser
    public ResponseEntity<HinhAnh> upload(@RequestParam("sachId")Integer sachId, @RequestParam("file") MultipartFile file) throws IOException {
        String url=fileStorageService.saveFile(file);
        HinhAnh hinhAnh=new HinhAnh();
        Optional<Sach> sach=sachService.findSachById(sachId);
        if(!sach.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        hinhAnh.setSach(sach.get());
        hinhAnh.setDuLieuAnh(url);
        hinhAnh.setIsDaiDien(false);
        HinhAnh saved=hinhAnhService.addHinhAnh(hinhAnh);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }
    @PostMapping("/upload-multiple")//loginuser
    public ResponseEntity<?> uploadMultiple(@RequestParam("sachId") Integer sachId, @RequestParam("file") MultipartFile[] files) throws IOException {
        Optional<Sach> sach=sachService.findSachById(sachId);
        if(!sach.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<HinhAnh> hinhAnhs=new ArrayList<>();
        for(MultipartFile file : files){
            String url=fileStorageService.saveFile(file);
            HinhAnh ha=new HinhAnh();
            ha.setSach(sach.get());
            ha.setDuLieuAnh(url);
            ha.setIsDaiDien(false);
            hinhAnhs.add(ha);
        }
        hinhAnhService.addMultipleHinhAnh(hinhAnhs);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PutMapping("/sach/{sachId}/hinhanh/{maHinhAnh}")//loginuser
    public ResponseEntity<HinhAnh> updateHinhAnhDaiDien(@PathVariable Integer sachId,@PathVariable Integer maHinhAnh) throws Exception {
       hinhAnhService.updateHinhAnhDaiDien(sachId,maHinhAnh);
       return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/sach/{sachId}")//public
    public List<HinhAnh> getHinhAnhBySachId(@PathVariable Integer sachId) {
        return hinhAnhService.getHinhAnhBySachId(sachId);
    }

    @GetMapping("/{id}")//public
    public ResponseEntity<HinhAnh> getHinhAnhById(@PathVariable Integer id) {
        return ResponseEntity.ok(hinhAnhService.getHinhAnhById(id));
    }

    @DeleteMapping("/{id}")//loginuser
    public ResponseEntity<?> deleteHinhAnhById(@PathVariable Integer id) {
        hinhAnhService.deleteHinhAnhById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/admin/get")//admin
    public List<HinhAnh> getAllHinhAnh() {
        return hinhAnhService.getAllHinhAnh();
    }

    @DeleteMapping("/admin/sach/{sachId}")//admin
    public ResponseEntity<Void> deleteHinhAnhBySachId(@PathVariable Integer sachId) {
        hinhAnhService.deleteHinhAnhBySachId(sachId);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("/anhDaiDien")//loginuser
    public List<HinhAnh> getHinhAnhDaiDien(Integer sachId) {
        return hinhAnhService.getHinhAnhDaiDien(sachId);
    }
}
