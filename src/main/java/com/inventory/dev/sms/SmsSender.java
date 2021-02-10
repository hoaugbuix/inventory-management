package com.inventory.dev.sms;

public interface SmsSender {
    void sendSms(SmsRequest smsRequest);

    // or maybe void sendSms(String phoneNumber, String message);
}
