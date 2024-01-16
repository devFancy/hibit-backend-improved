package com.hibitbackendrefactor.post.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hibitbackendrefactor.post.domain.Content;
import com.hibitbackendrefactor.post.domain.Exhibition;
import com.hibitbackendrefactor.post.domain.Title;
import com.hibitbackendrefactor.post.domain.TogetherActivity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCreateResponse {
    private Long id;
    private Title title;
    private Content content;
    private Exhibition exhibition;
    private int exhibitionAttendance;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime possibleTimes;
    private String openChatUrl;
    private TogetherActivity togetherActivity;

    public PostCreateResponse(final Long id, final Title title, final Content content, final Exhibition exhibition, final int exhibitionAttendance,
                              final LocalDateTime possibleTimes, final String openChatUrl, final TogetherActivity togetherActivity) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.exhibition = exhibition;
        this.exhibitionAttendance = exhibitionAttendance;
        this.possibleTimes = possibleTimes;
        this.openChatUrl = openChatUrl;
        this.togetherActivity = togetherActivity;
    }
}
