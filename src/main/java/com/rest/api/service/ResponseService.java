package com.rest.api.service;

import com.rest.api.model.CommonResult;
import com.rest.api.model.ListResult;
import com.rest.api.model.SingleResult;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseService {

    @Getter
    public enum CommonResponse {
        SUCCESS(0, "성공"),
        FAIL(-1, "실패");

        int code;
        String msg;

        CommonResponse(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

    public <T> SingleResult<T> getSingleResult(T data) {
        SingleResult<T> result = new SingleResult<>();
        result.setData(data);
        setSuccessResult(result);
        return result;
    }

    public <T> ListResult<T> getListResult(List<T> list) {
        ListResult<T> result = new ListResult<>();
        result.setList(list);
        setSuccessResult(result);
        return result;
    }

    public CommonResult getSuccessResult() {
        CommonResult result = new CommonResult();
        setSuccessResult(result);
        return result;
    }

    public CommonResult getFailResult(int code, String msg) {
        CommonResult result = new CommonResult();
        result.setSuccess(false);
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    private void setFailResult(CommonResult result) {
        result.setSuccess(false);
        result.setCode(CommonResponse.FAIL.getCode());
        result.setMsg(CommonResponse.FAIL.getMsg());
    }

    private void setSuccessResult(CommonResult result) {
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }
}
