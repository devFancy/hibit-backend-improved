package com.hibitbackendrefactor.post.application;

import com.hibitbackendrefactor.global.s3.S3UniqueFileName;
import com.hibitbackendrefactor.global.s3.S3UploadService;
import com.hibitbackendrefactor.member.domain.Member;
import com.hibitbackendrefactor.member.domain.MemberRepository;
import com.hibitbackendrefactor.post.domain.*;
import com.hibitbackendrefactor.post.dto.request.PostCreateRequest;
import com.hibitbackendrefactor.profile.domain.Profile;
import com.hibitbackendrefactor.profile.domain.ProfileRepository;
import com.hibitbackendrefactor.profile.exception.NotFoundProfileException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final PostImageRepository postImageRepository;
    private final S3UploadService s3UploadService;
    private final ProfileRepository profileRepository;

    public PostService(final PostRepository postRepository, final MemberRepository memberRepository,
                       final PostImageRepository postImageRepository, final S3UploadService s3UploadService,
                       final ProfileRepository profileRepository) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
        this.postImageRepository = postImageRepository;
        this.s3UploadService = s3UploadService;
        this.profileRepository = profileRepository;
    }

    public Long save(final Long memberId, final PostCreateRequest request, final List<MultipartFile> multipartFiles) throws IOException {
        validateMember(memberId);
        Member foundMember = memberRepository.getById(memberId);

        PostCreateRequest newRequest = createPostRequest(request, multipartFiles);
        Post newPost = request.toEntity(request, foundMember);
        postRepository.save(newPost);


        //저장한 게시글ID를 가져와서 게시글 사진 저장
        savePostImages(newPost.getId(), newRequest.getImageUrls());
        return newPost.getId();
    }

    private void validateMember(final Long memberId) {
        if (!existProfile(memberId)) {
            throw new NotFoundProfileException("프로필을 등록해야 게시글을 저장할 수 있습니다.");
        }
    }

    private boolean existProfile(final Long memberId) {
        Profile profile = profileRepository.findByMemberId(memberId).orElse(null);
        return profile != null;
    }

    private static PostCreateRequest createPostRequest(final PostCreateRequest request, final List<MultipartFile> multipartFiles) {
        return new PostCreateRequest(
                request.getTitle(), request.getContent(), request.getExhibition(), request.getExhibitionAttendance(),
                request.getOpenChatUrl(), request.getTogetherActivity(), request.getPossibleTimes(),
                multipartFiles, request.getPostStatus());
    }

    private List<String> savePostImages(final Long postId, final List<MultipartFile> images) throws IOException {
        List<String> savedImages = new ArrayList<>();

        for (MultipartFile image : images)
            if (!image.isEmpty())
                savedImages.add(savePostImage(postId, image));
        return savedImages;
    }

    private String savePostImage(final Long postId, final MultipartFile image) throws IOException {
        String uniqueFileName = S3UniqueFileName.generateUniqueFileName(image.getOriginalFilename());
        String savedImageUrl = s3UploadService.saveFile(image, uniqueFileName);

        PostImage postImage = new PostImage(postRepository.getById(postId), savedImageUrl);
        postImageRepository.save(postImage);
        return savedImageUrl;
    }
}
