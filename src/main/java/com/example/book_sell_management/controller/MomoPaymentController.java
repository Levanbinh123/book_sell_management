//package com.example.book_sell_management.controller;
//import com.example.book_sell_management.entity.DonHang;
//import com.example.book_sell_management.entity.ThanhToan;
//import com.example.book_sell_management.repository.DonHangRepository;
//import com.example.book_sell_management.repository.ThanhToanRepository;
//import com.example.book_sell_management.service.impl.MomoPaymentService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Date;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/payment")
//@RequiredArgsConstructor
//public class MomoPaymentController {
//
//    private final MomoPaymentService momoPaymentService;
//    private final DonHangRepository donHangRepository;
//    private final ThanhToanRepository thanhToanRepository;
//
//    @PostMapping("/momo")
//    public ResponseEntity<?> createMomoPayment(@RequestParam Integer maDonHang) throws Exception {
//        DonHang donHang = donHangRepository.findById(maDonHang)
//                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));
//
//        Map<String, Object> momoRes = momoPaymentService.createMomoPayment(
//               donHang.getMaDonHang(),
//                (long) donHang.getTongTien()
//        );
//
//        ThanhToan thanhToan = new ThanhToan();
//        thanhToan.setLoaiThanhToan("MOMO");
//        thanhToan.setTrangThai("CHO_THANH_TOAN");
//        thanhToan.setSoTien(donHang.getTongTien());
//        thanhToan.setMomoOrderId((String) momoRes.get("orderId"));
//        thanhToan.setMomoRequestId((String) momoRes.get("requestId"));
//        thanhToan.setPayUrl((String) momoRes.get("payUrl"));
//        thanhToan.setPhuLucJson(new ObjectMapper().writeValueAsString(momoRes));
//        thanhToan.setDonHang(donHang);
//        thanhToan.setNgayThanhToan(new Date());
//
//        thanhToanRepository.save(thanhToan);
//
//        return ResponseEntity.ok(Map.of("payUrl", momoRes.get("payUrl")));
//    }
//
//    @PostMapping("/momo-ipn")
//    public ResponseEntity<String> momoIpn(@RequestBody Map<String, Object> body) {
//        try {
//            String orderId = (String) body.get("orderId");
//            String requestId = (String) body.get("requestId");
//            String resultCode = String.valueOf(body.get("resultCode"));
//
//            ThanhToan thanhToan = thanhToanRepository.findByMomoOrderIdAndMomoRequestId(orderId, requestId);
//            if (thanhToan == null) return ResponseEntity.badRequest().body("Not found");
//
//            if ("0".equals(resultCode)) {
//                thanhToan.setTrangThai("DA_THANH_TOAN");
//            } else {
//                thanhToan.setTrangThai("THAT_BAI");
//            }
//
//            thanhToan.setPhuLucJson(new ObjectMapper().writeValueAsString(body));
//            thanhToanRepository.save(thanhToan);
//
//            return ResponseEntity.ok("Success");
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("Error");
//        }
//    }
//}