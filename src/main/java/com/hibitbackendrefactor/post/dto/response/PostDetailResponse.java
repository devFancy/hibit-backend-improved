package com.hibitbackendrefactor.post.dto.response;

import com.hibitbackendrefactor.post.domain.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.hibitbackendrefactor.post.dto.response.PostResponse.AttendanceAndTogetherActivity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDetailResponse {
    private Long id;
    private Long writerId;
    private String writerName;
    private String writerImage;
    private String title;
    private String content;
    private String exhibition;
    private List<String> exhibitionAttendanceAndTogetherActivity;
    private List<PostPossibleTime> possibleTimes;
    private String openChatUrl;
    private PostStatus postStatus;
    private List<String> postImages;

    @Builder
    public PostDetailResponse(final Long id, final Long writerId, final String writerName, String writerImage
            , final String title, final String content, final String exhibition
            , final List<String> exhibitionAttendanceAndTogetherActivity, final List<PostPossibleTime> possibleTimes
            , final String openChatUrl, final PostStatus postStatus, final List<String> postImages) {
        this.id = id;
        this.writerId = writerId;
        this.writerName = writerName;
//        this.writerImage = writerImage;
        this.title = title;
        this.content = content;
        this.exhibition = exhibition;
        this.exhibitionAttendanceAndTogetherActivity = exhibitionAttendanceAndTogetherActivity;
        this.possibleTimes = possibleTimes;
        this.openChatUrl = openChatUrl;
        this.postStatus = postStatus;
        this.postImages = postImages;
    }

    public static PostDetailResponse of(final Post post, List<String> imageUrls) {
        return PostDetailResponse.builder()
                .id(post.getId())
                .id(post.getMember().getId())
                .writerName(post.getMember().getDisplayName())
                // 게시글 작성자 이미지 추가해야함
                .title(post.getTitle())
                .content(post.getContent())
                .exhibition(post.getExhibition())
                .exhibitionAttendanceAndTogetherActivity(AttendanceAndTogetherActivity(post.getExhibitionAttendance(), post.getTogetherActivity()))
                .possibleTimes(post.getPossibleTimes())
                .openChatUrl(post.getOpenChatUrl())
                .postStatus(post.getPostStatus())
                .postImages(imageUrls)
                .build();
    }
}
