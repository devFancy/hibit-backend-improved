package com.hibitbackendrefactor.post.domain;

import com.hibitbackendrefactor.common.BaseEntity;
import com.hibitbackendrefactor.member.domain.Member;
import lombok.Builder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
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

    @ElementCollection
    @CollectionTable(name = "post_possible_times", joinColumns = @JoinColumn(name = "post_id"))
    @Column(nullable = false)
    private List<PostPossibleTime> possibleTimes = new ArrayList<>();

    @Column(name = "open_chat_url", nullable = false)
    private String openChatUrl;

    @Column(name = "together_activity", nullable = false)
    @Enumerated(EnumType.STRING)
    private TogetherActivity togetherActivity;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<PostImage> postImages = new ArrayList<>();

    @Column(name = "post_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PostStatus postStatus;

    protected Post() {
    }

    @Builder
    public Post(final Member member, String title, final String content, final String exhibition, final int exhibitionAttendance
            , final List<PostPossibleTime> possibleTimes, final String openChatUrl, final TogetherActivity togetherActivity
            , final List<PostImage> postImages, final PostStatus postStatus) {
        this.member = member;
        this.title = new Title(title);
        this.content = new Content(content);
        this.exhibition = new Exhibition(exhibition);
        this.exhibitionAttendance = exhibitionAttendance;
        this.possibleTimes = possibleTimes;
        this.openChatUrl = openChatUrl;
        this.togetherActivity = togetherActivity;
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

    public Content getContent() {
        return content;
    }

    public Exhibition getExhibition() {
        return exhibition;
    }

    public int getExhibitionAttendance() {
        return exhibitionAttendance;
    }

    public List<PostPossibleTime> getPossibleTimes() {
        return possibleTimes;
    }

    public String getOpenChatUrl() {
        return openChatUrl;
    }

    public TogetherActivity getTogetherActivity() {
        return togetherActivity;
    }

    public List<PostImage> getPostImages() {
        return postImages;
    }

    public PostStatus getPostStatus() {
        return postStatus;
    }
}