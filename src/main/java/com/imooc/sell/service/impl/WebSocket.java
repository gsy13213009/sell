package com.imooc.sell.service.impl;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@ServerEndpoint("/webSocket")
@Slf4j
public class WebSocket {

    private Session session;
    private static CopyOnWriteArraySet<WebSocket> sWebSockets = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        sWebSockets.add(this);
        log.info("【websocket消息】有新的连接，总数: {}", sWebSockets.size());
    }

    @OnClose
    public void onClose() {
        sWebSockets.remove(this);
        log.info("[webSocket消息] 连接断开，总数：{}", sWebSockets.size());
    }

    @OnMessage
    public void onMessage(String message) {
        log.info("【websocket消息】收到客户端发送来的消息:{}", message);
    }

    public void sendMessage(String message) {
        for (final WebSocket webSocket : sWebSockets) {
            log.info("[websocket] 广播消息，message= {}", message);
            try {
                webSocket.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
