package com.hibitbackendrefactor.post.dto.response;

import com.hibitbackendrefactor.post.domain.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostCreateResponse {
    private Long id;
    private Title title;
    private Content content;
    private Exhibition exhibition;
    private int exhibitionAttendance;
    private LocalDateTime possibleTime;
    private String openChatUrl;
    private TogetherActivity togetherActivity;

    public PostCreateResponse(Long id, Title title, Exhibition exhibition, int exhibitionAttendance, LocalDateTime possibleTime, String openChatUrl, TogetherActivity togetherActivity, Content content) {
        this.id = id;
        this.title = title;
        this.exhibition = exhibition;
        this.exhibitionAttendance = exhibitionAttendance;
        this.possibleTime = possibleTime;
        this.openChatUrl = openChatUrl;
        this.togetherActivity = togetherActivity;
        this.content = content;
    }
}
