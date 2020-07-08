package com.example.separation.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 监听UpdateEvent事件一点发布，就执行onApplicationEvent
 * @author Szp
 * @version 1.0
 * @date 2020/7/7 17:34
 */
@Component
public class SmsListener implements ApplicationListener<UpdateEvent> {

    @Override
    public void onApplicationEvent(UpdateEvent updateEvent) {
        System.out.println("发送短信");
    }
}
