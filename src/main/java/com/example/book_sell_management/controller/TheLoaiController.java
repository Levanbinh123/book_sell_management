package com.example.book_sell_management.controller;

import com.example.book_sell_management.entity.Sach;
import com.example.book_sell_management.entity.TheLoai;
import com.example.book_sell_management.service.TheLoaiService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@Tag(name = "08 - Quản lý thể loại sách")
@RestController
@RequestMapping("/api/theloai")
public class TheLoaiController {
    @Autowired
    private TheLoaiService theLoaiService;
    @GetMapping("/{id}")//public
  public ResponseEntity <TheLoai> getTheLoaiById(@PathVariable Integer id){
      return ResponseEntity.ok(theLoaiService.getTheLoaiById(id));
  }
    @GetMapping//public
    ResponseEntity<List<TheLoai>> getAllTheLoai(){
        return ResponseEntity.ok(theLoaiService.getAllTheLoai());
    }
    @PostMapping("/add")//admin
    ResponseEntity<TheLoai> createTheLoai(@RequestBody TheLoai theLoai){
        return ResponseEntity.ok(theLoaiService.createTheLoai(theLoai));
    }
    @PutMapping("/update/{id}")//admin
   ResponseEntity<TheLoai> updateTheLoai(@PathVariable Integer id,@RequestBody TheLoai theLoai) throws Exception{
        return ResponseEntity.ok(theLoaiService.updateTheLoai(id, theLoai));
    }
    @DeleteMapping("/delete/{id}")//admin
    public ResponseEntity<?> deleteTheLoaiById(@PathVariable Integer id) throws Exception{
            theLoaiService.deleteTheLoaiById(id);
            return ResponseEntity.ok().build();
    }
    // Quan hệ với sách
    @GetMapping("/sach/{id}")//public
    public ResponseEntity<List<Sach>> getSachByTheLoaiId(@PathVariable Integer id){
        return ResponseEntity.ok(theLoaiService.getSachByTheLoaiId(id));
    }


}
