package com.example.separation.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author Szp
 * @version 1.0
 * @date 2020/7/7 17:28
 */
public class UpdateEvent extends ApplicationEvent {
    /**
     * 创建事件
     * @param source
     */
    public UpdateEvent(Object source) {
        super(source);
    }
}
