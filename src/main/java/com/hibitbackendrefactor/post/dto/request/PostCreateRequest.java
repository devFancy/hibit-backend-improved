package com.hibitbackendrefactor.post.dto.request;

import com.hibitbackendrefactor.post.domain.PostStatus;
import com.hibitbackendrefactor.post.domain.TogetherActivity;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostCreateRequest {

    @NotBlank(message = "제목은 1자 이상 30자 이하여야 합니다.")
    private String title;

    @NotBlank(message = "본문은 1자 이상 200자 이하여야 합니다.")
    private String content;

    @NotBlank(message = "전시회 제목은 1자 이상 50자 이하여야 합니다.")
    private String exhibition;

    private PostStatus postStatus;
    private int exhibitionAttendance;
    private LocalDateTime possibleTime;
    private String openChatUrl;
    private TogetherActivity togetherActivity;
    private List<MultipartFile> imageUrls;

    protected PostCreateRequest() {
    }

    public PostCreateRequest(String title, String content,
                             String exhibition, PostStatus postStatus,
                             int exhibitionAttendance, String openChatUrl,
                             TogetherActivity togetherActivity, LocalDateTime possibleTime, List<MultipartFile> imageUrls) {
        this.title = title;
        this.content = content;
        this.exhibition = exhibition;
        this.postStatus = postStatus;
        this.exhibitionAttendance = exhibitionAttendance;
        this.openChatUrl = openChatUrl;
        this.togetherActivity = togetherActivity;
        this.possibleTime = possibleTime;
        this.imageUrls = imageUrls;
    }
}

