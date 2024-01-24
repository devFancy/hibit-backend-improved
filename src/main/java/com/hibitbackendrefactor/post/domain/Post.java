package com.hibitbackendrefactor.post.domain;

import com.hibitbackendrefactor.common.BaseEntity;
import com.hibitbackendrefactor.member.domain.Member;
import lombok.Builder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "posts")
@Entity
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "members_id")
    private Member member;

    @Column(name = "title", nullable = false)
    @Embedded
    private Title title;

    @Column(name = "content", nullable = false)
    @Embedded
    private Content content;

    @Column(name = "exhibition", nullable = false)
    @Embedded
    private Exhibition exhibition;

    @Column(name = "exhibition_attendance", nullable = false)
    private int exhibitionAttendance;
    @Column(nullable = false)
    private LocalDateTime possibleTime;

    @Column(name = "open_chat_url", nullable = false)
    private String openChatUrl;

    @Column(name = "together_activity", nullable = false)
    @Enumerated(EnumType.STRING)
    private TogetherActivity togetherActivity;

    private String imageName;

    @Column(name = "post_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PostStatus postStatus;

    private int viewCount = 0;
    protected Post() {
    }

    @Builder
    public Post(final Member member, String title, final String content, final String exhibition, final int exhibitionAttendance
            , final LocalDateTime possibleTime, final String openChatUrl, final TogetherActivity togetherActivity
            , final String imageName, final PostStatus postStatus) {
        this.member = member;
        this.title = new Title(title);
        this.content = new Content(content);
        this.exhibition = new Exhibition(exhibition);
        this.exhibitionAttendance = exhibitionAttendance;
        this.possibleTime = possibleTime;
        this.openChatUrl = openChatUrl;
        this.togetherActivity = togetherActivity;
        this.imageName = imageName;
        this.postStatus = postStatus;
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public String getTitle() {
        return title.getValue();
    }

    public String getContent() {
        return content.getValue();
    }

    public String getExhibition() {
        return exhibition.getTitle();
    }

    public int getExhibitionAttendance() {
        return exhibitionAttendance;
    }

    public LocalDateTime getPossibleTime() {
        return possibleTime;
    }

    public String getOpenChatUrl() {
        return openChatUrl;
    }

    public TogetherActivity getTogetherActivity() {
        return togetherActivity;
    }

    public String getImageName() {
        return imageName;
    }

    public PostStatus getPostStatus() {
        return postStatus;
    }

    public int getViewCount() {
        return viewCount;
    }
}