package com.hibitbackendrefactor.post.dto.response;

import com.hibitbackendrefactor.post.domain.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
    private String postImage;
    private LocalDate createDateTime;


    public PostResponse(Long id, String title, String exhibition
            , List<String> exhibitionAttendanceAndTogetherActivity, PostStatus postStatus
            , String postImage, LocalDate createDateTime) {
        this.id = id;
        this.title = title;
        this.exhibition = exhibition;
        this.exhibitionAttendanceAndTogetherActivity = exhibitionAttendanceAndTogetherActivity;
        this.postStatus = postStatus;
        this.postImage = postImage;
        this.createDateTime = createDateTime;
    }

