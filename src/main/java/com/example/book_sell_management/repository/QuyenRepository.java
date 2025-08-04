package com.example.book_sell_management.repository;
import com.example.book_sell_management.entity.Quyen;
import org.springframework.data.jpa.repository.JpaRepository;
public interface QuyenRepository extends JpaRepository<Quyen, Integer> {
    public Quyen findByTenQuyen(String tenQuyen);
    boolean existsByTenQuyen(String tenQuyen);
}
