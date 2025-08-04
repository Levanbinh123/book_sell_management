//    package com.example.book_sell_management.service.impl;
//
//    import com.example.book_sell_management.utils.SignatureUtil;
//    import com.fasterxml.jackson.databind.ObjectMapper;
//    import okhttp3.*;
//
//    import org.springframework.stereotype.Service;
//
//    import java.util.LinkedHashMap;
//    import java.util.Map;
//    import java.util.UUID;
//
//    @Service
//    public class MomoPaymentService {
//
//        private final String endpoint = "https://test-payment.momo.vn/v2/gateway/api/create";
//        private final String partnerCode = "MOMOXXXX20220419";
//        private final String accessKey = "F8BBA842ECF85";
//        private final String secretKey = "K951B6PE1waDMi640xX08PD3vg6EkVlz";
//        private final String redirectUrl = "https://your-site.com/return";
//        private final String ipnUrl = "https://your-site.com/ipn";
//
//        public Map<String, Object> createMomoPayment(Integer orderId, long amount) throws Exception {
//            String requestId = UUID.randomUUID().toString();
//            String requestType = "captureWallet";
//            String orderInfo = "Thanh toán đơn hàng " + orderId;
//            String extraData = "";
//
//            String rawSignature = "accessKey=" + accessKey +
//                    "&amount=" + amount +
//                    "&extraData=" + extraData +
//                    "&ipnUrl=" + ipnUrl +
//                    "&orderId=" + orderId +
//                    "&orderInfo=" + orderInfo +
//                    "&partnerCode=" + partnerCode +
//                    "&redirectUrl=" + redirectUrl +
//                    "&requestId=" + requestId +
//                    "&requestType=" + requestType;
//
//            String signature = SignatureUtil.hmacSHA256(rawSignature, secretKey);
//
//            Map<String, Object> payload = new LinkedHashMap<>();
//            payload.put("partnerCode", partnerCode);
//            payload.put("requestId", requestId);
//            payload.put("amount", amount);
//            payload.put("orderId", orderId);
//            payload.put("orderInfo", orderInfo);
//            payload.put("redirectUrl", redirectUrl);
//            payload.put("ipnUrl", ipnUrl);
//            payload.put("lang", "vi");
//            payload.put("extraData", extraData);
//            payload.put("requestType", requestType);
//            payload.put("signature", signature);
//
//            OkHttpClient client = new OkHttpClient();
//            ObjectMapper mapper = new ObjectMapper();
//
//            RequestBody body = RequestBody.create(
//                    mapper.writeValueAsString(payload),
//                    MediaType.parse("application/json")
//            );
//
//            Request request = new Request.Builder()
//                    .url(endpoint)
//                    .post(body)
//                    .build();
//
//            try (Response response = client.newCall(request).execute()) {
//                String res = response.body().string();
//                return mapper.readValue(res, Map.class);
//            }
//        }
//    }
