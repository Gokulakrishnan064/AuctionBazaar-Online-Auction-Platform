package com.auction.app.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.auction.app.service.impl.MailServiceImpl;

@RestController
public class EmailController {

    @Autowired
    private MailServiceImpl emailService;

    @GetMapping("/sendEmail")
    public String sendEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String body) {
    	return emailService.sendEmail(to, subject, body);
    }
}
