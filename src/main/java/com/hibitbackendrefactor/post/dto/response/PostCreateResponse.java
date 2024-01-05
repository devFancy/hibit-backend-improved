package com.hibitbackendrefactor.post.dto.response;

import com.hibitbackendrefactor.post.domain.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCreateResponse {
    private Long id;
    private Title title;
    private Content content;
    private Exhibition exhibition;
    private int exhibitionAttendance;
    private List<PostPossibleTime> possibleTimes;
    private String openChatUrl;
    private TogetherActivity togetherActivity;

    public PostCreateResponse(final Long id, final Title title, final Content content, final Exhibition exhibition, final int exhibitionAttendance,
                              final List<PostPossibleTime> possibleTimes, final String openChatUrl, final TogetherActivity togetherActivity) {
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
