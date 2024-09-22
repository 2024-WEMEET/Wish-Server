package wish.wishServer.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wish.wishServer.member.domain.Member;
import wish.wishServer.member.dto.MemberResponse;
import wish.wishServer.member.dto.MemberSaveDto;
import wish.wishServer.member.exception.AuthErrorCode;
import wish.wishServer.member.exception.AuthException;
import wish.wishServer.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional // 데이터 변경이 있는 곳에 Transactional 걸어주기
    public MemberResponse save(MemberSaveDto dto) {

        memberRepository.findByUsername(dto.username())
                .ifPresent(member -> {
                    throw new AuthException(AuthErrorCode.MEMBER_DUPLICATION);
                });

        Member member = Member.builder()
                .username(dto.username())
                .nickname(dto.nickname())
                .build();

        memberRepository.save(member);
        return MemberResponse.toMember(member);
    }
}
