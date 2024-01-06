package com.hibitbackendrefactor.post.dto.response;

import com.hibitbackendrefactor.post.domain.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDetailResponse {
    private Long id;
    private String writerName;
    private Long writerId;
    private String writerImage;
    private String title;
    private String content;
    private String exhibition;
    private int exhibitionAttendance;
    private List<PostPossibleTime> possibleTimes;
    private String openChatUrl;
    private TogetherActivity togetherActivity;
    private PostStatus postStatus;
    private List<PostImage> postImage;

    @Builder
    public PostDetailResponse(final Long id, final String writerName, final Long writerId, String writerImage
            , final String title, final String content, final String exhibition
            , final int exhibitionAttendance, final List<PostPossibleTime> possibleTimes
            , final String openChatUrl, final TogetherActivity togetherActivity
            , final PostStatus postStatus, final List<PostImage> postImage) {
        this.id = id;
        this.writerName = writerName;
        this.writerId = writerId;
        this.writerImage = writerImage;
        this.title = title;
        this.content = content;
        this.exhibition = exhibition;
        this.exhibitionAttendance = exhibitionAttendance;
        this.possibleTimes = possibleTimes;
        this.openChatUrl = openChatUrl;
        this.togetherActivity = togetherActivity;
        this.postStatus = postStatus;
        this.postImage = postImage;
    }

    public static PostDetailResponse of(final Post post) {
        return PostDetailResponse.builder()
                .id(post.getId())
                .writerName(post.getMember().getDisplayName())
                .id(post.getMember().getId())
                // 게시글 작성자 이미지 추가해야함
                .title(post.getTitle())
                .content(post.getContent())
                .exhibition(post.getExhibition())
                .exhibitionAttendance(post.getExhibitionAttendance())
                .possibleTimes(post.getPossibleTimes())
                .openChatUrl(post.getOpenChatUrl())
                .togetherActivity(post.getTogetherActivity())
                .postStatus(post.getPostStatus())
                .postImage(post.getPostImages())
                .build();
    }
}
