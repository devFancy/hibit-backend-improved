package com.hibitbackendrefactor.post.dto.request;

import com.hibitbackendrefactor.post.domain.PostStatus;
import com.hibitbackendrefactor.post.domain.TogetherActivity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostUpdateServiceRequest {
    private String title;
    private String content;
    private String exhibition;
    private int exhibitionAttendance;
    private LocalDateTime possibleTime;
    private String openChatUrl;
    private TogetherActivity togetherActivity;
    private String imageName;
    private PostStatus postStatus;

    @Builder
    public PostUpdateServiceRequest(final String title, final String content, final String exhibition,
                                    final int exhibitionAttendance, final LocalDateTime possibleTime, final String openChatUrl,
                                    final TogetherActivity togetherActivity, final String imageName, final PostStatus postStatus) {
        this.title = title;
        this.content = content;
        this.exhibition = exhibition;
        this.exhibitionAttendance = exhibitionAttendance;
        this.possibleTime = possibleTime;
        this.openChatUrl = openChatUrl;
        this.togetherActivity = togetherActivity;
        this.imageName = imageName;
        this.postStatus = postStatus;
    }
}
