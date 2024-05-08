//package com.example.RealVoice_Backend.domain.user;
//
//import com.google.firebase.FirebaseException;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseAuthException;
//import com.google.firebase.auth.FirebaseToken;
//import com.google.firebase.auth.PhoneAuthProvider;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//@Service
//public class UserService {
//
//	 private final FirebaseAuth firebaseAuth;
//
//	    @Autowired
//	    public UserService() {
//	        firebaseAuth = FirebaseAuth.getInstance();
//	    }
//
//	    public String generatePhoneVerificationCode(String phoneNumber) throws FirebaseAuthException {
//	        // 전화번호에 대한 인증 코드 생성
//	        try {
//	            PhoneAuthProvider.getInstance().verifyPhoneNumber(
//	                    phoneNumber,
//	                    60L,
//	                    java.util.concurrent.TimeUnit.SECONDS,
//	                    null
//	            );
//	            return "Verification code sent to " + phoneNumber;
//	        } catch (FirebaseException e) {
//	            throw new FirebaseAuthException(e.getMessage(), e.getCause());
//	        }
//	    }
//
//	    public boolean verifyPhoneNumberWithCode(String phoneNumber, String code) throws FirebaseAuthException {
//	        // 사용자가 입력한 코드로 전화번호 검증
//	        try {
//	            PhoneAuthProvider.getInstance().verifyPhoneNumber(
//	                    phoneNumber,
//	                    60L,
//	                    java.util.concurrent.TimeUnit.SECONDS,
//	                    null
//	            ).verify(code);
//	            return true; // Verification successful
//	        } catch (FirebaseException e) {
//	            throw new FirebaseAuthException(e.getMessage(), e.getCause());
//	        }
//	    }
//}