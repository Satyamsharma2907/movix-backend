package org.niit.EmailNotificationService.controller;

import org.niit.EmailNotificationService.domain.EmailData;
import org.niit.EmailNotificationService.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/emailservice")
public class EmailController
{
    @Autowired
    private IEmailService emailService;

    /*
    POST
    http://localhost:65500/mail-app/send-mail
     */
    @PostMapping("/send-mail")
    public ResponseEntity<?> sendEmail(@RequestBody EmailData emailData){
        return new ResponseEntity<>(emailService.sendEmail(emailData), HttpStatus.OK);
    }

    @PostMapping("/sendOtp/{email}")
    public ResponseEntity<?> sendOtp1(@PathVariable String email) {
        return new ResponseEntity<>(emailService.sendEmail2(email), HttpStatus.OK);
    }
    /*
     GET
     http://localhost:5673/mail-app/check/{Otp}
      */
    @GetMapping("/check/{Otp}")
    public ResponseEntity<?> checkOtp(@PathVariable int Otp) {
        return new ResponseEntity<>(emailService.checkOtp(Otp), HttpStatus.OK);
    }
}
