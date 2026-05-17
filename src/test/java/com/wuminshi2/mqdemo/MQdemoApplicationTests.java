package com.wuminshi2.mqdemo;

import com.wuminshi2.mqdemo.utils.Send;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MQdemoApplicationTests {
    @Autowired
    Send send;
    @Test
    void sendMessage(){
        send.sendMessage("hello world");
    }
    @Test
    void contextLoads() {
    }

}
