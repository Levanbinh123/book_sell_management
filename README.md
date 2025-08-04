# 📚 Book Sell Management API

Dự án RESTful API dùng để quản lý hệ thống bán sách online, hỗ trợ người dùng đăng ký, đăng nhập, tìm kiếm sách, quản lý thể loại, danh sách yêu thích, đơn hàng và thanh toán.
## 🛠️ Công nghệ sử dụng
- **Spring Boot 3**
- **Spring Security + JWT**
- **Spring Data JPA**
- **MySQL**
- **Swagger UI (OpenAPI v3)**
- **Lombok**
- **Maven**
- **EmailSender**
---
##  Cách chạy dự án
1. **Clone dự án:**
   ```bash
   -git clone https://github.com/Levanbinh123/book_sell_management.git
   -cd book-sell-management
2. **cấu hinh database:**
   ```bash
   -spring.datasource.url=jdbc:mysql://localhost:3306/your_db
   -spring.datasource.username=root
   -spring.datasource.password=your_password
3. **chạy dự án:**
   ```bash
  -mvn spring-boot:run
4. **truy cập swagger:**
   ```bash
   -http://localhost:8080/swagger-ui/index.html
