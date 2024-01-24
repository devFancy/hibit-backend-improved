package com.hibitbackendrefactor.post.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hibitbackendrefactor.auth.dto.LoginMember;
import com.hibitbackendrefactor.member.domain.Member;
import com.hibitbackendrefactor.post.domain.Post;
import com.hibitbackendrefactor.post.domain.PostStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.hibitbackendrefactor.post.dto.response.PostResponse.AttendanceAndTogetherActivity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDetailResponse {
    private static final String HIBIT_BASIC_IMAGE = "https://hibitbucket.s3.ap-northeast-2.amazonaws.com/hibit-image.png";

    private Long id;
    private Long writerId;
    private String writerName;
    private String title;
    private String content;
    private String exhibition;
    private List<String> exhibitionAttendanceAndTogetherActivity;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime possibleTime;
    private String openChatUrl;
    private PostStatus postStatus;
    private String imageName;
    private int viewCount;

    @Builder
    public PostDetailResponse(final Long id, final Long writerId, final String writerName
            , final String title, final String content, final String exhibition
            , final List<String> exhibitionAttendanceAndTogetherActivity, final LocalDateTime possibleTime
            , final String openChatUrl, final PostStatus postStatus, final String imageName, int viewCount) {
        this.id = id;
        this.writerId = writerId;
        this.writerName = writerName;
        this.title = title;
        this.content = content;
        this.exhibition = exhibition;
        this.exhibitionAttendanceAndTogetherActivity = exhibitionAttendanceAndTogetherActivity;
        this.possibleTime = possibleTime;
        this.openChatUrl = openChatUrl;
        this.postStatus = postStatus;
        this.imageName = imageName;
        this.viewCount = viewCount;
    }

    public static PostDetailResponse of(final Post post, final LoginMember loginMember) {
        return PostDetailResponse.builder()
                .id(post.getId())
                .writerId(loginMember.getId())
                .writerName(post.getMember().getDisplayName())
                .title(post.getTitle())
                .content(post.getContent())
                .exhibition(post.getExhibition())
                .exhibitionAttendanceAndTogetherActivity(AttendanceAndTogetherActivity(post.getExhibitionAttendance(), post.getTogetherActivity()))
                .possibleTime(post.getPossibleTime())
                .openChatUrl(post.getOpenChatUrl())
                .postStatus(post.getPostStatus())
                .imageName(post.getImageName())
                .viewCount(post.getViewCount())
                .build();
    }

    private static String findWriterImage(final Member member) {
        if(!member.getMainImage().isEmpty()) {
            return member.getMainImage();
        }
        return HIBIT_BASIC_IMAGE;
    }
}
