package com.dream.chat.ScheduleTask;

import com.dream.chat.cache.RedisService;
import com.dream.chat.cache.dto.ProjectDTO;
import com.dream.chat.entity.Project;
import com.dream.chat.service.OrderService;
import com.dream.chat.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Service
public class RedisListener implements MessageListener {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private RedisService redisService;


    @Override
    public void onMessage(Message message, byte[] bytes) {
        String expiredKey=message.toString();
        if(expiredKey.contains("PROJECT:")){
            //ProjectDTO projectDTO = redisService.get(expiredKey,ProjectDTO.class);
            String projectId = expiredKey.substring(8);
            Project project = projectService.getById(projectId);
            //System.out.println(projectId);
            System.out.println("监听结束");
            projectService.overProject(project.getUserId(),project);
        }
    }

    public static void main(String[] args) {
        String a = "123456789";
        System.out.println(a.substring(6));
    }
}
