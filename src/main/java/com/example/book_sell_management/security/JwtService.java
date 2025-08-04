package com.example.book_sell_management.security;
import com.example.book_sell_management.entity.NguoiDung;
import com.example.book_sell_management.entity.Quyen;
import com.example.book_sell_management.repository.NguoiDungRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtService {
    @Autowired
    private NguoiDungRepository nguoiDungRepository;
    @Autowired
    private UserDetailsService userDetailsService;
    public static final String SERECT = "639792F423F4528482Basd4D6251655468576D5A71asdad347437";
    // Tạo JWT dựa trên tên đang nhập
    public String generateToken(String tenDangNhap) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(tenDangNhap);

        Map<String, Object> claims = new HashMap<>();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.toList());
        claims.put("roles", roles);

        return createToken(claims, tenDangNhap);
    }
    public String extractUserByToken(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }
    //Tạo JWT với các claim đã chọn
    private  String createToken(Map<String, Object> claims, String tenDangNhap){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(tenDangNhap)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+30*60*1000)) // JWT hết hạn sau 30 phút
                .signWith(SignatureAlgorithm.HS256,getSigneKey())
                .compact();
    }
    public static String getTenDangNhapFromToken(String token) {
        token = token.substring(7);
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigneKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject(); //  dùng getSubject()
    }
    // Lấy serect key
    private static Key getSigneKey(){
        return Keys.hmacShaKeyFor(SERECT.getBytes(StandardCharsets.UTF_8));
    }
    // Trích xuất thông tin
    private Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(getSigneKey()).parseClaimsJws(token).getBody();
    }
    // Trích xuất thông tin cho 1 claim
    public <T> T extractClaim(String token, Function<Claims, T> claimsTFunction){
        final Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }
    // Kiểm tra tời gian hết hạn từ JWT
    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }
    // Kiểm tra tời gian hết hạn từ JWT
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }
    // Kiểm tra cái JWT đã hết hạn
    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
    // Kiểm tra tính hợp lệ
    public Boolean validateToken(String token, UserDetails userDetails){
        final String tenDangNhap = extractUsername(token);
        System.out.println(tenDangNhap);
        return (tenDangNhap.equals(userDetails.getUsername())&&!isTokenExpired(token));
    }
}
