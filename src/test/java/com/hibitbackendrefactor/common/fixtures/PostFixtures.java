package com.hibitbackendrefactor.common.fixtures;

import com.hibitbackendrefactor.post.domain.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class PostFixtures {
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

}
