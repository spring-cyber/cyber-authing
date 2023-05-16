package com.cyber.authing.exception;

import com.alibaba.fastjson.JSONObject;
import com.cyber.domain.constant.HttpResultCode;
import com.cyber.domain.entity.DataResponse;
import com.cyber.domain.entity.Response;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author yanwei
 * @desc
 * @since 2023/5/9
 */
@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Response handleException(final Exception exception,
                                    final HttpServletRequest request) {
        JSONObject param = new JSONObject();
        param.put("uri", request.getRequestURI());
        param.put("error_timestamp", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
        DataResponse<JSONObject> responseData = new DataResponse<>();
        responseData.setCode(HttpResultCode.SERVER_ERROR.getCode());
        responseData.setMessage(exception.getMessage());
        responseData.setData(param);
        return responseData;
    }
}
