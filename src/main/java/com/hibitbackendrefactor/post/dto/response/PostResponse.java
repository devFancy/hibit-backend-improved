package com.hibitbackendrefactor.post.dto.response;

import com.hibitbackendrefactor.post.domain.Post;
import com.hibitbackendrefactor.post.domain.PostStatus;
import com.hibitbackendrefactor.post.domain.TogetherActivity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostResponse {
    private Long id;
    private String title;
    private String exhibition;
    private List<String> exhibitionAttendanceAndTogetherActivity;
    private PostStatus postStatus;
    private String imageName;
    private LocalDateTime createDateTime;

    @Builder
    public PostResponse(Long id, String title, String exhibition
            , List<String> exhibitionAttendanceAndTogetherActivity, PostStatus postStatus
            , String imageName, LocalDateTime createDateTime) {
        this.id = id;
        this.title = title;
        this.exhibition = exhibition;
        this.exhibitionAttendanceAndTogetherActivity = exhibitionAttendanceAndTogetherActivity;
        this.postStatus = postStatus;
        this.imageName = imageName;
        this.createDateTime = createDateTime;
    }

    public static PostResponse from(final Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .exhibition(post.getExhibition())
                .exhibitionAttendanceAndTogetherActivity(AttendanceAndTogetherActivity(post.getExhibitionAttendance(), post.getTogetherActivity()))
                .postStatus(post.getPostStatus())
                .imageName(post.getImageName())
                .createDateTime(post.getCreateDateTime())
                .build();
    }

    public static List<String> AttendanceAndTogetherActivity(final int exhibitionAttendance, final TogetherActivity togetherActivity) {
        List<String> attendanceAndTogetherActivity = new ArrayList<>();
        attendanceAndTogetherActivity.add(exhibitionAttendance + "인 관람");
        attendanceAndTogetherActivity.add(togetherActivity.getText());
        return attendanceAndTogetherActivity;
    }
}
