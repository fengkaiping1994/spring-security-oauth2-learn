package com.github.kersen.oauth2.wechat;

import org.json.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RequestMapping("/wechat")
public class WechatOauth {
    public final static String appId = "wx007cda20d737915e";
    public final static String appSecret = "42eaee523fde203ba4d7cc30bfdf9f32";

    @RequestMapping("oauth")
    public void oauth(HttpServletResponse httpServletResponse) throws IOException {
        String redirect_uri = "http://127.0.0.1:80/wechat/invoke";

        try {
            redirect_uri = URLEncoder.encode(redirect_uri, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String authorizedUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
                "appid=" + appId +
                "&redirect_uri=" + redirect_uri +
                "&response_type=code" +
                "&scope=" + "snsapi_userinfo" +
                "&state=STATE" +
                "#wechat_redirec";

        httpServletResponse.sendRedirect(authorizedUrl);


    }

    /**
     * 如果用户同意授权，页面将跳转至 redirect_uri/?code=CODE&state=STATE。
     */

    @RequestMapping("/invoke")
    public void oauthInvoke(HttpServletRequest httpServletRequest) throws JSONException {
        String code = httpServletRequest.getParameter("code");
        String state = httpServletRequest.getParameter("state");
//        System.out.println(code);
//        System.out.println(state);
        //认证服务器
        String accessTokenURL = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid=" + appId +
                "&secret=" + appSecret +
                "&code=" + code +
                "&grant_type=authorization_code";

        //
        String s = HttpUtils.sendGet(accessTokenURL);
        JSONObject jsonObject = JSONObject.parseObject(s);
        System.out.println(jsonObject);
        String access_token = jsonObject.getString("access_token");
        String openid = jsonObject.getString("openid");

        //
        String userInfoURL = " https://api.weixin.qq.com/sns/userinfo?" +
                "access_token=" + access_token +
                "&openid=" + openid +
                "&lang=zh_CN";

        String s1 = HttpUtils.sendGet(userInfoURL);
        JSONObject jsonObject1 = JSONObject.parseObject(s1);
        System.out.println(jsonObject1);


    }
}
