package com.cyber.authing.controller;

import com.cyber.authing.entity.domain.ActivityLog;
import com.cyber.authing.entity.request.ActivityLogRequest;
import com.cyber.authing.service.ActivityLogService;
import com.cyber.domain.entity.DataResponse;
import com.cyber.domain.entity.IdRequest;
import com.cyber.domain.entity.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import javax.validation.Valid;

@RestController
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class ActivityLogController {

    @Autowired
    ActivityLogService activityLogService;

    @GetMapping("/activitylog/search")
    public Response searchActivityLog(@Valid ActivityLogRequest request) {
        ActivityLog activitylog = request.toEvent(request.getTenantCode());
        return activityLogService.selectPage(activitylog);
    }


    @GetMapping("/activitylog")
    public Response selectOneActivityLog(@Valid IdRequest idRequest) {
        DataResponse<ActivityLog> response = new DataResponse<ActivityLog>();
        ActivityLog activityLog = new ActivityLog();
        activityLog.setId(idRequest.getId());
        activityLog.setTenantCode(idRequest.getTenantCode());
        activityLog = activityLogService.selectOne(activityLog);
        response.setData(activityLog);
        return response;
    }
}
