package com.example.book_sell_management.controller;

import com.example.book_sell_management.dto.sachDto.AddSachDto;
import com.example.book_sell_management.dto.sachDto.UpdateSachDto;
import com.example.book_sell_management.entity.Sach;
import com.example.book_sell_management.service.SachService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
@Tag(name = "06 - Quản lý sách")
@RestController
@RequestMapping("/api/sach")
public class SachController {
@Autowired
private SachService sachService;
    @GetMapping("/{id}")//public
   public Optional<Sach> findSachById(@PathVariable Integer id){
        return sachService.findSachById(id);
    }
    @GetMapping//public
   public List<Sach> findAllSach(){
        return sachService.findAllSach();
    }
    @PutMapping("/capnhat/{id}")//staff
    public ResponseEntity<Sach> updateSach(@PathVariable Integer id,@RequestBody UpdateSachDto updateSachDto) throws Exception{
        return new ResponseEntity<>(sachService.updateSach(id, updateSachDto), HttpStatus.OK);
    }
    @PostMapping("/add")//staff
    public Sach addSach(@RequestBody AddSachDto addSachDto){
    return sachService.addSach(addSachDto);
    }
    @DeleteMapping("/delete/{id}")//admin
    public void deleteSachById(@PathVariable Integer id){
        sachService.deleteSachById(id);
    }
    @GetMapping("/conains")//public
    List<Sach> findByTenContains(@RequestParam String keyword){
        return sachService.findByTenContains(keyword);
    }
    @GetMapping("/theloai")//public
    List<Sach> findByTheLoai(@RequestParam String theLoai){
        return sachService.findByTheLoai(theLoai);
    }
    @GetMapping("/tacgia")//  Lọc theo thể loại//public
    public List<Sach> findByTacGia(@RequestParam String tacGia){
        return sachService.findByTacGia(tacGia);
    }
    @GetMapping("/{min}/{max}")//public
    public List<Sach> findByGiaBetween(@PathVariable double min,@PathVariable double max){
        return sachService.findByGiaBetween(min, max);
    }
}
