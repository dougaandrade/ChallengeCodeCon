package com.dev.codecon.dto;

import lombok.Data;

@Data

public class TeamInsights {

    private String team;
    private Long total_Members;
    private Long leaders;
    private Long completed_projects;
    private double active_percentage;

}
