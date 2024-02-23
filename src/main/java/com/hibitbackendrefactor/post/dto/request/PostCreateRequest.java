package com.hibitbackendrefactor.post.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hibitbackendrefactor.member.domain.Member;
import com.hibitbackendrefactor.post.domain.Post;
import com.hibitbackendrefactor.post.domain.PostStatus;
import com.hibitbackendrefactor.post.domain.TogetherActivity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCreateRequest {

    @NotBlank(message = "제목은 1자 이상 30자 이하여야 합니다.")
    private String title;

    @NotBlank(message = "본문은 1자 이상 200자 이하여야 합니다.")
    private String content;

    @NotBlank(message = "전시회 제목은 1자 이상 50자 이하여야 합니다.")
    private String exhibition;

    private int exhibitionAttendance;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime possibleTime;
    private String openChatUrl;
    private TogetherActivity togetherActivity;
    private String imageName;
    private PostStatus postStatus;

    @Builder
    public PostCreateRequest(final String title, final String content,
                             final String exhibition, final int exhibitionAttendance, final String openChatUrl,
                             final TogetherActivity togetherActivity, final LocalDateTime possibleTime,
                             final String imageName, final PostStatus postStatus) {
        this.title = title;
        this.content = content;
        this.exhibition = exhibition;
        this.exhibitionAttendance = exhibitionAttendance;
        this.openChatUrl = openChatUrl;
        this.togetherActivity = togetherActivity;
        this.possibleTime = possibleTime;
        this.imageName = imageName;
        this.postStatus = postStatus;
    }

    public Post toEntity(final Member foundMember, final PostCreateRequest request) {
        return Post.builder()
                .member(foundMember)
                .title(request.getTitle())
                .content(request.getContent())
                .exhibition(request.getExhibition())
                .exhibitionAttendance(request.getExhibitionAttendance())
                .possibleTime(request.getPossibleTime())
                .openChatUrl(request.getOpenChatUrl())
                .togetherActivity(request.getTogetherActivity())
                .imageName(request.getImageName())
                .postStatus(PostStatus.HOLDING)
                .build();
    }
}

