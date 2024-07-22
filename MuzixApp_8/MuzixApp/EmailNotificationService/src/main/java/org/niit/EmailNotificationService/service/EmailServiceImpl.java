package org.niit.EmailNotificationService.service;

import org.niit.EmailNotificationService.domain.EmailData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements IEmailService
{
      @Autowired
      private JavaMailSender javaMailSender;

      @Value("${spring.mail.username}")
      private String sender;
      public int otp;
      private long otpTimestamp;

    @Override
    public String sendEmail(EmailData emailData) {
        try
        {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(emailData.getReceiver());
            simpleMailMessage.setTo(emailData.getReceiver());
            simpleMailMessage.setText(emailData.getMessageBody());
            simpleMailMessage.setSubject(emailData.getSubject());
            javaMailSender.send(simpleMailMessage);
            return "Success";
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            return "failure";
        }
    }

    @Override
    public int sendEmail2(String email) {
        try {
            // generate a 4-digit OTP
            otp = (int) (Math.random() * 9000) + 1000;
            otpTimestamp = System.currentTimeMillis();
            System.out.println(otpTimestamp);
            // create a new EmailData object
            EmailData emailData = new EmailData();
            emailData.setReceiver(email);
            emailData.setSubject("Your OTP");
            emailData.setMessageBody("Your OTP is " + otp);
            // send the email
            checkOtp(otp);
            sendEmail(emailData);

        } catch (Exception e) {
            e.printStackTrace();
            // return "Sending OTP failed...";
        }
        return otp;
    }

    @Override
    public int checkOtp(int Otp) {
        if (System.currentTimeMillis() - otpTimestamp > 2 * 60 * 1000)
        {

            otp=0;
            System.out.println(Otp);
//            return false;
        }
//         check;
        System.out.println(otp);
//        check = otp == Otp;
        return otp;
    }
}
