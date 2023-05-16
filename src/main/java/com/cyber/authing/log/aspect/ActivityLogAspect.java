package com.cyber.authing.log.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cyber.authing.log.annotation.OperationLog;
import com.cyber.authing.log.entity.LogInfo;
import com.cyber.domain.constant.HttpResultCode;
import com.cyber.domain.exception.BusinessException;
import com.cyber.infrastructure.toolkit.UUIDs;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Template;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;


@Aspect
@Configuration
public class ActivityLogAspect {

    private static Logger logger = LoggerFactory.getLogger(ActivityLogAspect.class);

    public static final ConcurrentLinkedQueue<LogInfo> queue = new ConcurrentLinkedQueue<>();

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");

    /**
     * 队列最大长度，防止队列过长内存泄漏
     */
    private static final Integer MAX_RECORD = 10 * 1000;

    @Around(value = "@annotation(operationLog)")
    public Object doAround(ProceedingJoinPoint point, OperationLog operationLog) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        long start = System.currentTimeMillis();
        HttpServletRequest request = attributes.getRequest();
        // 获取请求参数
        // JSONObject userInfo = userSession.getUserInfo();
        LogInfo logInfo = new LogInfo();
        logInfo.setRequestTime(simpleDateFormat.format(new Date()));
        logInfo.setUserId("testid");
        logInfo.setUserName("testname");
        logInfo.setProductCode("test");
        logInfo.setRequestUrl(request.getRequestURI());
        String requestBody = "{}";
        // 转换成JSONObject
        try {
            JSONObject jsonObject = JSON.parseObject(requestBody);
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.putAll(jsonObject.getInnerMap());
            paramMap.put("userCode", logInfo.getUserName());
            logInfo.setRequestParam(requestBody);
            logInfo.setContent(processTemplateIntoString(operationLog.content(), paramMap));
        } catch (Exception e) {
            logger.info("request body is {}", requestBody);
        }
        Object object = point.proceed();
        logInfo.setExecResult(JSON.toJSONString(object));
        logInfo.setExecTime(System.currentTimeMillis() - start);
        add(logInfo);
        return object;
    }

    public void add(LogInfo logInfo) {
        // 如果超过队列最大长度限制，移除头部元素
        if (queue.size() >= MAX_RECORD) {
            queue.poll();
        }
        queue.add(logInfo);
    }

    private static String processTemplateIntoString(String templateContent, Map<String, Object> params) {
        if (CollectionUtils.isEmpty(params)) {
            return templateContent;
        }

        try {
            freemarker.template.Configuration configuration = new freemarker.template.Configuration(freemarker.template.Configuration.getVersion());
            String uuid = UUIDs.generateShortUuid();
            StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
            stringTemplateLoader.putTemplate(uuid, templateContent);
            configuration.setTemplateLoader(stringTemplateLoader);
            Template template = configuration.getTemplate(uuid, "utf-8");
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, params);
        } catch (Exception e) {
            logger.error("Execute ProcessTemplateIntoString, But Is Exception", e);
            throw new BusinessException("Execute ProcessTemplateIntoString, But Is Exception",
                    HttpResultCode.SERVER_ERROR.getCode());
        }
    }
}
