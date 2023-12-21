package com.hibitbackendrefactor.member.dto;

import com.hibitbackendrefactor.member.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
public class MemberIsProfileResponse {
    private Long idx;
    private boolean Isprofile;

    public MemberIsProfileResponse(@NotNull Member entity){
        this.idx = entity.getId();
        this.Isprofile=entity.getIsprofile();
    }

}
