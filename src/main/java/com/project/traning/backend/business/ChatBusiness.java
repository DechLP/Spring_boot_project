package com.project.traning.backend.business;

import com.project.traning.backend.exception.BaseException;
import com.project.traning.backend.exception.ChatException;
import com.project.traning.backend.model.ChatMessage;
import com.project.traning.backend.model.ChatMessageRequest;
import com.project.traning.backend.util.SecurityUtil;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class ChatBusiness {

    private final SimpMessagingTemplate template;

    public ChatBusiness(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void post(ChatMessageRequest request) throws BaseException {
        Optional<String> opt = SecurityUtil.getCurrentUserId();
        if (opt.isEmpty()) {
            throw ChatException.accessDenied();
        }

        final String destination = "/topic/chat";

        ChatMessage payload = new ChatMessage();
        payload.setFrom(opt.get());
        payload.setMessage(request.getMessage());

        template.convertAndSend(destination, payload);
    }

}
