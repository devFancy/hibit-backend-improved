package com.hibitbackendrefactor.member.application;


import com.hibitbackendrefactor.member.domain.Member;
import com.hibitbackendrefactor.member.domain.MemberRepository;
import com.hibitbackendrefactor.member.dto.MemberResponse;
import com.hibitbackendrefactor.member.exception.NotFoundMemberException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberResponse findById(final Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(NotFoundMemberException::new);

        return new MemberResponse(member);
    }
}
