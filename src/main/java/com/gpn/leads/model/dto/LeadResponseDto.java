package com.gpn.leads.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LeadResponseDto {
    private Long id;
    private String name;
    private String phone;
    private String comment;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
