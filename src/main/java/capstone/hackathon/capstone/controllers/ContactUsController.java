package capstone.hackathon.capstone.controllers;

import capstone.hackathon.capstone.service.EmailService;
import capstone.hackathon.capstone.web.dto.ContactUsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contactUs")
public class ContactUsController {
    @Autowired
    EmailService emailService;
    @GetMapping("/Contact")
    public ResponseEntity<String> contactUs(@RequestBody ContactUsDto contactUsDto)
    {
        emailService.sendMail("ihackathonhelpdesk@gmail.com",contactUsDto.getSubject(),"User:"+contactUsDto.getName()+ "\nEmail:"+contactUsDto.getEmail()+"\nMessage: "+contactUsDto.getContent());
        emailService.sendMail(contactUsDto.getEmail(),"iHackathon: Query received","Dear "+contactUsDto.getName()+" .We have received your message. Our team will contact with you shortly.\nRegards,\nTeam-ihackathon.");
        return ResponseEntity.ok("Message has been sent");
    }
}
