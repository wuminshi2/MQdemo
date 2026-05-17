package com.wuminshi2.mqdemo.controller;

import com.wuminshi2.mqdemo.utils.Send;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
    @Autowired
    Send send;

    @PostMapping("/send")
    public void send(@RequestParam(value = "message",defaultValue = "") String message) {
        send.sendMessage(message);
    }
}
