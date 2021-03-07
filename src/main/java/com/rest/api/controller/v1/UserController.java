package com.rest.api.controller.v1;

import com.rest.api.advice.exception.CUserNotFoundException;
import com.rest.api.entity.User;
import com.rest.api.model.CommonResult;
import com.rest.api.model.ListResult;
import com.rest.api.model.SingleResult;
import com.rest.api.repo.UserJpaRepo;
import com.rest.api.service.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"1. User"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class UserController {
    private final UserJpaRepo userJpaRepo;
    private final ResponseService responseService;

    @ApiOperation(value = "회원 조회", notes = "모든 회원을 조회한다")
    @GetMapping(value = "/user")
    public ListResult<User> findAllUser() {
        return responseService.getListResult(userJpaRepo.findAll());
    }

    @ApiOperation(value = "회원 단건 조회", notes = "userId로 회원 조회")
    @GetMapping(value = "/user/{seq}")
    public SingleResult<User> findUserById(@ApiParam(value = "회원 번호", required = true) @PathVariable long seq,
                                           @ApiParam(value = "언어", defaultValue = "ko") @RequestParam String lang) {
        return responseService.getSingleResult(userJpaRepo.findById(seq).orElseThrow(CUserNotFoundException::new));
    }

    @ApiOperation(value = "회원 저장", notes = "회원을 저장한다")
    @PostMapping(value = "/user")
    public SingleResult<User> save(@ApiParam(value = "회원 아이디", required = true) @RequestParam String uid,
                                   @ApiParam(value = "회원 이름", required = true) @RequestParam String name) {
        User user = User.builder()
                .uid(uid)
                .name(name)
                .build();
        return responseService.getSingleResult(userJpaRepo.save(user));
    }

    @ApiOperation(value = "회원 수정", notes = "회원을 수정한다")
    @PutMapping(value = "/user")
    public SingleResult<User> modify(
            @ApiParam(value = "회원 번호", required = true) @RequestParam long seq,
            @ApiParam(value = "회원 아이디", required = true) @RequestParam String uid,
            @ApiParam(value = "회원 이름", required = true) @RequestParam String name) {

        User user = User.builder()
                .seq(seq)
                .uid(uid)
                .name(name)
                .build();

        return responseService.getSingleResult(userJpaRepo.save(user));
    }

    @ApiOperation(value = "회원 삭제", notes = "회원을 삭제한다")
    @DeleteMapping(value = "/user/{seq}")
    public CommonResult delete(@ApiParam(value = "회원 번호", required = true) @PathVariable long seq) {
        userJpaRepo.deleteById(seq);
        return responseService.getSuccessResult();
    }
}
