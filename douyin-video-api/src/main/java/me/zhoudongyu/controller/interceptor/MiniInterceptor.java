package me.zhoudongyu.controller.interceptor;

import me.zhoudongyu.utils.JSONResult;
import me.zhoudongyu.utils.JsonUtils;
import me.zhoudongyu.utils.RedisOperator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Steve
 * @date 2019/06/18
 */
public class MiniInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisOperator redis;

    private static final String USER_REDIS_TOKEN = "user-redis-token";

    /**
     * 拦截请求，在controller调用之前
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String userId = request.getHeader("userId");
        String userToken = request.getHeader("userToken");

        if (StringUtils.isNoneBlank(userId) && StringUtils.isNoneBlank(userToken)) {
            String uniqueToken = redis.get(USER_REDIS_TOKEN + ":" + userId);
            if (StringUtils.isBlank(uniqueToken)) {
                System.out.println("请登录...");
                returnErrorResponse(response, JSONResult.errorTokenMsg("请登录..."));
                return false;
            } else {
                if (!uniqueToken.equals(userToken)) {
                    System.out.println("账号被挤出...");
                    returnErrorResponse(response, JSONResult.errorTokenMsg("账号被挤出..."));
                    return false;
                }
            }
        } else {
            System.out.println("请登录...");
            returnErrorResponse(response, JSONResult.errorTokenMsg("请登录..."));
            return false;
        }
        return true;
    }


    private void returnErrorResponse(HttpServletResponse response, JSONResult result)
            throws IOException {
        OutputStream out = null;
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/json");
            out = response.getOutputStream();
            out.write(JsonUtils.objectToJson(result).getBytes("utf-8"));
            out.flush();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * 请求controller之后，渲染视图之前
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 请求controller之后，渲染视图之后
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
