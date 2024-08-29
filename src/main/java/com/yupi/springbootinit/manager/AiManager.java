package com.yupi.springbootinit.manager;

import com.baidubce.qianfan.Qianfan;
import com.baidubce.qianfan.model.chat.ChatResponse;
import com.yupi.yucongming.dev.client.YuCongMingClient;
import com.yupi.yucongming.dev.model.DevChatRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @version v1.0
 * @author OldGj 2024/8/28
 * @apiNote AI 调用api
 */
@Service
public class AiManager {


    @Resource
    private YuCongMingClient yuCongMingClient;

    /**
     * 鱼聪明AI
     * @param modelId
     * @param message
     * @return
     */
    public String doChat(long modelId, String message) {
        DevChatRequest chatRequest = new DevChatRequest();
        chatRequest.setMessage(message);
        chatRequest.setModelId(modelId);
        return yuCongMingClient.doChat(chatRequest).getData().getContent();
    }

}
