package com.hibitbackendrefactor.post.dto.request;

import com.hibitbackendrefactor.member.domain.Member;
import com.hibitbackendrefactor.post.domain.Post;
import com.hibitbackendrefactor.post.domain.PostPossibleTime;
import com.hibitbackendrefactor.post.domain.PostStatus;
import com.hibitbackendrefactor.post.domain.TogetherActivity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCreateRequest {

    @NotBlank(message = "제목은 1자 이상 30자 이하여야 합니다.")
    private String title;

    @NotBlank(message = "본문은 1자 이상 200자 이하여야 합니다.")
    private String content;

    @NotBlank(message = "전시회 제목은 1자 이상 50자 이하여야 합니다.")
    private String exhibition;

    private PostStatus postStatus;
    private int exhibitionAttendance;
    private List<PostPossibleTime> possibleTimes;
    private String openChatUrl;
    private TogetherActivity togetherActivity;
    private List<MultipartFile> imageUrls;

    @Builder
    public PostCreateRequest(final String title, final String content,
                             final String exhibition, final int exhibitionAttendance, final String openChatUrl,
                             final TogetherActivity togetherActivity, final List<PostPossibleTime> possibleTimes,
                             final List<MultipartFile> imageUrls, final PostStatus postStatus) {
        this.title = title;
        this.content = content;
        this.exhibition = exhibition;
        this.exhibitionAttendance = exhibitionAttendance;
        this.openChatUrl = openChatUrl;
        this.togetherActivity = togetherActivity;
        this.possibleTimes = possibleTimes;
        this.imageUrls = imageUrls;
        this.postStatus = postStatus;
    }

    public Post toEntity(final PostCreateRequest request, final Member member) {
        return Post.builder()
                .member(member)
                .title(request.getTitle())
                .content(request.getContent())
                .exhibition(request.getExhibition())
                .exhibitionAttendance(request.getExhibitionAttendance())
                .possibleTimes(request.getPossibleTimes())
                .openChatUrl(request.getOpenChatUrl())
                .togetherActivity(request.getTogetherActivity())
                .postStatus(PostStatus.HOLDING)
                .build();
    }
}

