package com.example.book_sell_management.security;

public class Enpoints {
    public static final String front_end_host = "http://localhost:3000";
    //public
    public static final String[] PUBLIC_GET_ENDPOINS = {
            "/api/sach/**",
            "/api/sach",
            "/api/theloai/**",
            "/api/theloai",
            "/api/hinhanh/**"


    };
    public static final String[] PUBLIC_PUT_ENDPOINS = {
            "/api/auth/reset-password",
            "/api/auth/forgetPassword",
    };
    public static final String[] PUBLIC_DELETE_ENDPOINS = {
    };
    public static final String[] PUBLIC_POST_ENDPOINS = {
            "/api/auth/dang-ky",
            "/api/auth/kich-hoat",
            "/api/auth/dang-nhap",
            "/api/auth/get-maResetPassWord",
    };
    //login user
    public static final String[] USERLOGIN_GET_ENDPOINS = {
            "/api/user/gettokenuser",
            "/api/danhgia/trungBinhDanhGia/sach/**",
            "/api/danhgia/sach/**",
            "/api/hinhanh/anhDaiDien",
            "/api/donHang/**"
    };
    public static final String[] USERLOGIN_DELETE_ENDPOINS = {
            "/api/sachYeuThich/**",
            "/api/hinhanh/**",
            "/api/donHang/**"
    };
    public static final String[] USERLOGIN_PUT_ENDPOINS = {
            "/api/danhgia/**"
            ,"/api/hinhanh/sach/{sachId}/hinhanh/{maHinhAnh}",
            "/api/donHang/**"
    };
    public static final String[] USERLOGIN_POST_ENDPOINS = {
            "/api/danhgia/sach/**",
            "/api/sachYeuThich/**",
            "/api/hinhanh/upload",
            "/api/hinhanh/upload-multiple",
            "/api/donHang/**"

    };
    //admin
    public static final String[] ADMIN_GET_ENDPOINS = {
            "/api/user/**",
            "/api/user",
            "/api/hinhanh/admin/**",
            "/api/donHang/admin/**",
            "/api/donHang/admin"
    };
    public static final String[] ADMIN_DELETE_ENDPOINS = {
            "/api/user/**",
            "/api/theloai/delete/**",
            "/api/hinhanh/admin/**",
            "/api/donHang/admin/**"
    };
    public static final String[] ADMIN_PUT_ENDPOINS = {
            "/api/theloai/update/**"
    };
    public static final String[] ADMIN_POST_ENDPOINS = {
            "/api/theloai/add"
    };
    //staff
    public static final String[] STAFF_GET_ENDPOINS = {
    };
    public static final String[] STAFF_DELETE_ENDPOINS = {
    };
    public static final String[] STAFF_PUT_ENDPOINS = {
            "/api/sach/capnhat/**",
            "/api/donHang/xacnhan/**",
            "/api/donHang/xulygiaohang-momo/**",
            "/api/donHang/xulygiaohang-cod/**",
            "/api/donHang/hoantatdonhang/**",
            "/api/donHang/huydonhang/**"
    };
    public static final String[] STAFF_POST_ENDPOINS = {
            "/api/sach/add"
    };

}
