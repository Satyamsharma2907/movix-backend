package org.niit.EmailNotificationService.config;

import org.niit.EmailNotificationService.domain.EmailData;
import org.niit.EmailNotificationService.service.IEmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MailConsumer
{
    @Autowired
    private IEmailService emailService;

    /**
     * It listens to the mail_queue and when it receives a message it sends an email to the receiver.
     *
     * @param emailDTO The object that will be received from the queue.
     */
    //@RabbitListener means this method works as listener , to connected to this queue.
    //listener means-whenever the data is entered in mail queue,data will process
    @RabbitListener(queues = "userMailQueue")
    public void getEmailDtoFromQueue(EmailDTO emailDTO){
        EmailData emailData =new EmailData(emailDTO.getReceiver(),
                emailDTO.getMessageBody() ,emailDTO.getSubject(),null);
        System.out.println(emailService.sendEmail(emailData));
    }
}
