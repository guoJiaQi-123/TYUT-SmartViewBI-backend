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

    /**
     * 百度千帆大模型
     * @param role
     * @param content
     * @return
     */
   /* public String doChatByQianFan(String role, String content) {
        
        Qianfan qianfan = new Qianfan(AccessKey, SecretKey);
        ChatResponse resp = qianfan.chatCompletion()
                .model("ERNIE-Speed-128K")
                .addMessage(role, "你是一个数据分析师和前端开发专家，接下来我会按照以下固定格式给你提供内容：\n" +
                        "分析需求：\n" +
                        "{数据分析的需求或者目标}\n" +
                        "原始数据：\n" +
                        "{csv格式的原始数据，用,作为分隔符}\n" +
                        "请根据这两部分内容，按照以下指定格式生成内容（此外不要输出任何多余的开头、结尾、注释）\n" +
                        "【【【【【\n" +
                        "{前端 Echarts V5 的 option 配置对象json代码，合理地将上述的原始数据进行可视化}\n" +
                        "【【【【【\n" +
                        "{明确的数据分析结论、越详细越好，不要生成多余的注释}\n" +
                        "输出示例：\n" +
                        "【【【【【\n" +
                        "{\n" +
                        "  \"tooltip\": {\n" +
                        "    \"trigger\": \"axis\"\n" +
                        "  },\n" +
                        "  \"legend\": {\n" +
                        "    \"data\": [\"人数\"]\n" +
                        "  },\n" +
                        "  \"toolbox\": {\n" +
                        "    \"feature\": {\n" +
                        "      \"saveAsImage\": {}\n" +
                        "    }\n" +
                        "  },\n" +
                        "  \"grid\": {\n" +
                        "    \"left\": \"3%\",\n" +
                        "    \"right\": \"4%\",\n" +
                        "    \"bottom\": \"3%\",\n" +
                        "    \"containLabel\": true\n" +
                        "  },\n" +
                        "  \"xAxis\": {\n" +
                        "    \"type\": \"category\",\n" +
                        "    \"boundaryGap\": false,\n" +
                        "    \"data\": [\"1号\", \"2号\", \"3号\"]\n" +
                        "  },\n" +
                        "  \"yAxis\": {\n" +
                        "    \"type\": \"value\"\n" +
                        "  },\n" +
                        "  \"series\": [\n" +
                        "    {\n" +
                        "      \"name\": \"人数\",\n" +
                        "      \"type\": \"line\",\n" +
                        "      \"stack\": \"总量\",\n" +
                        "      \"data\": [10, 20, 80]\n" +
                        "    }\n" +
                        "  ]\n" +
                        "};\n" +
                        "【【【【【\n" +
                        "根据提供的数据，我们可以得出以下结论： \n" +
                        "1. 从1号到3号，网站的用户数量呈现了显著的增长趋势，从最初的10人增长到80人。\n" +
                        "2. 尤其是3号，相较于前两日，用户人数有了大幅度的提升，从20人增加到80人，增长率为300%。\n" +
                        "3. 这表明在这段时间内，网站可能采取了某些有效的推广策略或者是网站内容吸引了更多的用户访问。 \n" +
                        "需要注意的是，这些结论仅基于三天内用户数量的变化，长期的趋势和用户留存率等更深层次的分析需要更多的数据支持。\n"+
                        content)
                .execute();
        return resp.getResult();
    }*/

}
