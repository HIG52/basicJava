package com.example.basicsamplesite.presentation.auth.dto;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 회원가입 요청 DTO - Presentation Layer
 */
@Getter
public class SignupRequest {
    
    @NotBlank(message = "사용자 ID는 필수입니다")
    @Size(min = 4, max = 20, message = "사용자 ID는 4~20자 사이여야 합니다")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "사용자 ID는 영문과 숫자만 사용 가능합니다")
    private String userId;
    
    @NotBlank(message = "사용자명은 필수입니다")
    @Size(min = 2, max = 50, message = "사용자명은 2~50자 사이여야 합니다")
    private String username;
    
    @NotBlank(message = "이메일은 필수입니다")
    @Email(message = "올바른 이메일 형식이 아닙니다")
    private String email;
    
    @NotBlank(message = "비밀번호는 필수입니다")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다")
    private String password;
    
    // 선택 필드들
    private String phone;
    private String zipcode;
    private String address;
    private String addressDetail;
    
    // Default constructor for JSON deserialization
    public SignupRequest() {
    }
    
    public SignupRequest(String userId, String username, String email, String password, String phone, 
                        String zipcode, String address, String addressDetail) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.zipcode = zipcode;
        this.address = address;
        this.addressDetail = addressDetail;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }
}
