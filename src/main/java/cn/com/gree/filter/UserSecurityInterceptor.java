package cn.com.gree.filter;

import cn.com.gree.utils.Result;
import net.sf.json.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class UserSecurityInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String user = String.valueOf(request.getSession().getAttribute("user"));
        if(user == null || user.isEmpty() || "null".equals(user)){
            response.setContentType("text/plain; chartset=UTF-8");
            PrintWriter writer = response.getWriter();
            writer.write(JSONObject.fromObject(new Result(false,"noSession")).toString());
            writer.flush();
            writer.close();
            return false;
        }
        return true;
    }
}
