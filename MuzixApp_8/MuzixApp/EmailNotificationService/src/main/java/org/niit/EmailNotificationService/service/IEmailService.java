package org.niit.EmailNotificationService.service;

import org.niit.EmailNotificationService.domain.EmailData;

public interface IEmailService
{
    public abstract String sendEmail(EmailData emailData);
    public int sendEmail2(String email);
    public int checkOtp(int Otp);
}
