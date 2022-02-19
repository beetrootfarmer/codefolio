package com.codefolio.controller;


import com.codefolio.service.EmailService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping ("/emailConfirm")
    public ResponseEntity<String> emailConfirm(@RequestBody String email) throws Exception {

        System.out.println(email);
        String confirm = emailService.sendSimpleMessage(email);

        return ResponseEntity.ok(confirm);
    }

}
