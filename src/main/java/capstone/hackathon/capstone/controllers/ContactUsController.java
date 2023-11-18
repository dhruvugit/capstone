package capstone.hackathon.capstone.controllers;

import capstone.hackathon.capstone.security.UserInfoUserDetails;
import capstone.hackathon.capstone.service.EmailService;
import capstone.hackathon.capstone.web.dto.ContactUsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/contactUs")
public class ContactUsController {
    @Autowired
    EmailService emailService;
    @GetMapping("/Contact")
    public ResponseEntity<String> contactUs(@RequestBody ContactUsDto contactUsDto)
    {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfoUserDetails user = (UserInfoUserDetails) authentication.getPrincipal();



        ExecutorService emailExecutor = Executors.newSingleThreadExecutor();
        emailExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    emailService.sendMail("ihackathonhelpdesk@gmail.com",contactUsDto.getSubject(),"User:"+contactUsDto.getContent()+"\nEmail:"+user.getEmail()+"\nMessage: "+contactUsDto.getContent());
                    emailService.sendMail(user.getEmail(),"iHackathon: Query received","\n We have received your message. Our team will contact with you shortly.\n\nRegards,\nTeam-ihackathon.");

                } catch (Exception e) {
                    System.out.println("failed" + e);
                }
            }
        });
        emailExecutor.shutdown();


        return ResponseEntity.ok("Message has been sent");
    }
}




