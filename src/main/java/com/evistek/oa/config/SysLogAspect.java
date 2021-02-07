package com.evistek.oa.config;

import com.evistek.oa.annotation.Operation;
import com.evistek.oa.dao.EmployeeDao;
import com.evistek.oa.dao.SystemLogDao;
import com.evistek.oa.entity.SystemLog;
import com.evistek.oa.utils.Constant;
import com.evistek.oa.utils.JwtUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/9
 */
@Aspect
@Component
public class SysLogAspect {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private SystemLogDao systemLogDao;
    private EmployeeDao employeeDao;
    private SystemLog systemLog = new SystemLog();

    public SysLogAspect(SystemLogDao systemLogDao, EmployeeDao employeeDao) {
        this.systemLogDao = systemLogDao;
        this.employeeDao = employeeDao;
    }

    /**
     * 切入点
     */
    @Pointcut("@annotation(com.evistek.oa.annotation.Operation)")
    public void controllerAspect() {
    }

    /**
     * 返回通知
     *
     * @param joinPoint
     * @param result
     */
    @AfterReturning(value = "controllerAspect()", returning = "result")
    public void afterReturn(JoinPoint joinPoint, Object result) {
        this.addLog(joinPoint, Constant.METHOD_EXECUTE_SUCCESS);
    }

    /**
     * 异常通知
     */
    @AfterThrowing(pointcut = "controllerAspect()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        this.addLog(joinPoint, Constant.METHOD_EXECUTE_FAILED);
    }

    private void addLog(JoinPoint joinPoint, int resultState) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] paramNames = signature.getParameterNames();
        Object[] paramValues = joinPoint.getArgs();
        String type = signature.getMethod().getAnnotation(Operation.class).description();
        String apiName = null;
        String method = null;
        String user = null;
        Map<String, Object> paramMap = new HashMap();
        if (paramNames.length != paramValues.length) {
            logger.info("aop log :  get parameters error ");
            return;
        }
        String ip = "";
        for (int i = 0; i < paramNames.length; i++) {
            String paramName = paramNames[i];
            Object paramValue = paramValues[i];
            paramMap.put(paramName, paramValue);
            if (paramName.equals("email") && paramValue != null) {
                user = paramValue.toString();
                continue;
            }
            if (paramName.equals("token")) {
                user = JwtUtil.getEmail(paramValue.toString());
                continue;
            }
            if (paramValue instanceof HttpServletRequest) {
                HttpServletRequest request = (HttpServletRequest) paramValue;
                ip = request.getRemoteAddr();
                apiName = request.getRequestURL().toString();
                method = request.getMethod();
                continue;
            }
        }
        this.saveLog(user, type, apiName, paramMap.toString(), method, ip, resultState);
    }

    private synchronized void saveLog(String user, String type, String apiName, String parameter,
                                      String method, String ip, int resultState) {
        systemLog.setUser(user);
        systemLog.setType(type);
        systemLog.setApiName(apiName);
        systemLog.setParameter(parameter);
        systemLog.setMethod(method);
        systemLog.setIp(ip);
        systemLog.setResult(resultState);
        systemLogDao.addSystemLog(systemLog);
    }
}
