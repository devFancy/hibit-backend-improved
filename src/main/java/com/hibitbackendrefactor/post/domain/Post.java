package com.hibitbackendrefactor.post.domain;

import com.hibitbackendrefactor.common.BaseEntity;
import com.hibitbackendrefactor.member.domain.Member;
import lombok.Builder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
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

    @Column(name = "open_chat_url", nullable = false)
    private String openChatUrl;

    @Column(name = "together_activity", nullable = false)
    @Enumerated(EnumType.STRING)
    private TogetherActivity togetherActivity;

    @Column(name = "content", nullable = false)
    @Embedded
    private Content content;

    @Column(name = "main_image", nullable = false)
    private String mainImage;

    @Column(name = "sub_image", nullable = false)
    @ElementCollection
    private List<String> subImage;

    @Column(name = "post_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PostStatus postStatus;

    @Column(nullable = false)
    private String possibleTime;

    protected Post() {
    }

    @Builder
    public Post(Member member, Title title, Exhibition exhibition, int exhibitionAttendance,
                String openChatUrl, TogetherActivity togetherActivity, Content content,
                String mainImage, List<String> subImage, PostStatus postStatus,
                String possibleTime) {
        this.member = member;
        this.title = title;
        this.exhibition = exhibition;
        this.exhibitionAttendance = exhibitionAttendance;
        this.openChatUrl = openChatUrl;
        this.togetherActivity = togetherActivity;
        this.content = content;
        this.mainImage = mainImage;
        this.subImage = subImage;
        this.postStatus = postStatus;
        this.possibleTime = possibleTime;
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

    public String getOpenChatUrl() {
        return openChatUrl;
    }

    public TogetherActivity getTogetherActivity() {
        return togetherActivity;
    }

    public Content getContent() {
        return content;
    }

    public String getMainImage() {
        return mainImage;
    }

    public List<String> getSubImage() {
        return subImage;
    }

    public PostStatus getPostStatus() {
        return postStatus;
    }

    public String getPossibleTime() {
        return possibleTime;
    }
}