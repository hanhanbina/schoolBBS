package com.xu.sladmin.entity;

/**
 * @Name RegistrationResponse
 * @Author 31671
 * @Date 2023-10-12 0:46
 * 响应信息
 */
public class RegistrationResponse {
    private boolean success;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
