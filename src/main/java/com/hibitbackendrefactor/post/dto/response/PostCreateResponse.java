package com.hibitbackendrefactor.post.dto.response;

import com.hibitbackendrefactor.post.domain.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostCreateResponse {
    private Long id;
    private Title title;
    private Exhibition exhibition;
    private int exhibitionAttendance;
    private LocalDateTime possibleTime;
    private String openChatUrl;
    private TogetherActivity togetherActivity;
    private Content content;
    private String mainImage;
    private List<String> subImage;

    public PostCreateResponse(Long id, Title title, Exhibition exhibition, int exhibitionAttendance, LocalDateTime possibleTime, String openChatUrl, TogetherActivity togetherActivity, Content content, String mainImage, List<String> subImage) {
        this.id = id;
        this.title = title;
        this.exhibition = exhibition;
        this.exhibitionAttendance = exhibitionAttendance;
        this.possibleTime = possibleTime;
        this.openChatUrl = openChatUrl;
        this.togetherActivity = togetherActivity;
        this.content = content;
        this.mainImage = mainImage;
        this.subImage = subImage;
    }
}
