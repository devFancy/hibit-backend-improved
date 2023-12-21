package com.hibitbackendrefactor.post.domain;

import com.hibitbackendrefactor.common.BaseEntity;
import com.hibitbackendrefactor.member.domain.Member;
import lombok.Builder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "posts")
@Entity
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @Column(name = "title", nullable = false)
    @Embedded
    private Title title;

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

    @Column(name = "content", nullable = false)
    @Embedded
    private Content content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostImage> postImages = new ArrayList<>();

    @Column(name = "post_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PostStatus postStatus;

    protected Post() {
    }

    @Builder
    public Post(Member member, String title, String exhibition, int exhibitionAttendance,
                LocalDateTime possibleTime, String openChatUrl, TogetherActivity togetherActivity,
                String content, List<PostImage> postImages , PostStatus postStatus) {
        this.member = member;
        this.title = new Title(title);
        this.exhibition = new Exhibition(exhibition);
        this.exhibitionAttendance = exhibitionAttendance;
        this.possibleTime = possibleTime;
        this.openChatUrl = openChatUrl;
        this.togetherActivity = togetherActivity;
        this.content = new Content(content);
        this.postImages = postImages;
        this.postStatus = postStatus;
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Title getTitle() {
        return title;
    }

    public Exhibition getExhibition() {
        return exhibition;
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

    public Content getContent() {
        return content;
    }

    public List<PostImage> getPostImages() {
        return postImages;
    }

    public PostStatus getPostStatus() {
        return postStatus;
    }
}