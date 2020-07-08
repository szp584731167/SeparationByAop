package com.example.separation.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author Szp
 * @version 1.0
 * @date 2020/7/7 17:37
 */
@Component
public class TsListener implements ApplicationListener<UpdateEvent> {
    @Override
    public void onApplicationEvent(UpdateEvent updateEvent) {
        System.out.println("推送");
    }
}
