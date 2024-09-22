package wish.wishServer.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import wish.wishServer.common.response.BasicResponse;
import wish.wishServer.common.response.ResponseUtil;
import wish.wishServer.member.dto.MemberResponse;
import wish.wishServer.member.dto.MemberSaveDto;
import wish.wishServer.member.service.MemberService;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public BasicResponse<MemberResponse> signUp(@Valid @RequestBody MemberSaveDto dto) {
        return ResponseUtil.success(memberService.save(dto));
    }
}
