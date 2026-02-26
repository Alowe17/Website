package com.example.Web_Service.model.dto.moderator.request;

import com.example.Web_Service.model.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SupportTicketNewDto {
    @NotNull(message = "ID не может быть пустым или отсутствовать!")
    private Integer id;
    private Status status;
    @NotBlank(message = "Ответ не может быть пустым!")
    private String answer;

    public SupportTicketNewDto (Integer id, Status status, String answer) {
        this.id = id;
        this.status = status;
        this.answer = answer;
    }

    public SupportTicketNewDto () {}

    public Integer getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public String getAnswer() {
        return answer;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}