package com.imooc.sell.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/webSocket")
@Slf4j
public class WebSocket {

    private Session session;

    //save webSocket connections
    private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<>();

    @OnOpen
    public void opOpen(Session session){
         this.session = session;
         webSocketSet.add(this);
         log.info("Build new webSocket connection!!, webSockets'size={}", webSocketSet.size());
    }

    @OnClose
    public void onClose(){
        webSocketSet.remove(this);
        log.info("Close this connection!! webSockets'size={}", webSocketSet.size());
    }

    @OnMessage
    public void onMessage(String message){
        log.info("Get new message from client, message={}", message);
    }

    public void sendMessage(String message) {
        for (WebSocket webSocket : webSocketSet) {
            log.info("Broadcast message to webSockets, message={}", message);
            try {
                webSocket.session.getBasicRemote().sendText(message);
            }catch (IOException e){
                log.error("WebSocket send message error");
            }
        }
    }
}
