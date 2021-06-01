package cn.best.approveservice.common.utils;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;

/**
 * @author zhuyl
 * WebSocket工具类
 * @date 2020-6-2 10:17
 */
public class WebSocketUtil {
    /**
     * 给指定的session用户发送消息
     * @param session 发送对象
     * @param map 要发送的而消息
     * @throws Exception
     */
    public static void sendMessageBySession(WebSocketSession session, Map<String, String> map) throws Exception{
        if(!CommonUtil.isNull(session)&&session.isOpen()){
            try {
                session.sendMessage(new TextMessage(JsonUtil.toJson(map)));
            } catch (Exception e) {
                session.close();
                String userId = (String) session.getAttributes().get("userId");
                System.out.println("当前websocket或许已经断开连接，发送消息失败：userId:"+userId);
            }
        }
    }

}
