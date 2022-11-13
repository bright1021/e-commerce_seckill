package com.shmtu.seckill.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MQReceiver {

    @RabbitListener(queues = "queue")
    public void receive(Object msg){
        log.info("接收消息："+ msg);
    }

    @RabbitListener(queues = "queue01")
    public void receive1(Object msg){
        log.info("接收消息："+ msg);
    }
    @RabbitListener(queues = "queue02")
    public void receive2(Object msg){
        log.info("接收消息："+ msg);
    }
    @RabbitListener(queues = "queue03")
    public void receive3(Object msg){
        log.info("接收消息："+ msg);
    }

}
