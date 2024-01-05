package com.hibitbackendrefactor.common.fixtures;

import com.hibitbackendrefactor.post.domain.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class PostFixtures {

    /* 게시글1 : 해시태크 */
    private static String imageUrl1 = "hashtag.png";
    private static String imageUrl2 = "hashtag.png";
    public static final String 게시글제목 = "프로젝트_해시테크";
    public static final String 게시글내용 = "프로젝트 해시 태크(http://projecthashtag.net/) 보러가실 분 있으면 아래 댓글 남겨주세요~";

    public static final String 전시회제목 = "PROJECT HASHTAG 2023 SELECTED ARTISTS";

    public static final int 전시관람인원 = 3;
    public static final List<PostPossibleTime>  전시관람희망날짜 = Arrays.asList(
            new PostPossibleTime(LocalDate.now(), DayHalf.AM),
            new PostPossibleTime(LocalDate.now(), DayHalf.PM)
    );

    public static final String 오픈채팅방Url = "http://projecthashtag.net/";
    public static final TogetherActivity 함께하고싶은활동 = TogetherActivity.EAT;

    public static final List<PostImage> 게시글이미지들 = Arrays.asList(
            new PostImage(imageUrl1),
            new PostImage(imageUrl2)
    );
    public static final PostStatus 모집상태 = PostStatus.HOLDING;


    /* 게시글2: 오스틴리 전시회 */
    private static String imageUrl11 = "ostin.png";
    private static String imageUrl22 = "ostin2.png";
    public static final String 게시글제목2 = "오스틴리 전시회";
    public static final String 게시글내용2 = "오스틴리 전시회 보고, 같이 담소하게 얘기 나누실 분 있으시면 아래 댓글 남겨주세요~";

    public static final String 전시회제목2 = "오스틴리 전시회";

    public static final int 전시관람인원2 = 3;
    public static final List<PostPossibleTime>  전시관람희망날짜2 = Arrays.asList(
            new PostPossibleTime(LocalDate.now(), DayHalf.AM),
            new PostPossibleTime(LocalDate.now(), DayHalf.PM)
    );

    public static final String 오픈채팅방Url2 = "http://ostin.net/";
    public static final TogetherActivity 함께하고싶은활동2 = TogetherActivity.EAT;

    public static final List<PostImage> 게시글이미지들2 = Arrays.asList(
            new PostImage(imageUrl11),
            new PostImage(imageUrl22)
    );
    public static final PostStatus 모집상태2 = PostStatus.HOLDING;


}
