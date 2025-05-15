package org.example.campuslifebackend.service.impl;

import org.example.campuslifebackend.service.WxService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信服务实现类
 */
@Service
public class WxServiceImpl implements WxService {
    @Value("${wx.appid}")
    private String appid;
    
    @Value("${wx.secret}")
    private String secret;
    
    private final RestTemplate restTemplate = new RestTemplate();
    
    @Override
    public String getWxOpenid(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid +
                "&secret=" + secret + "&js_code=" + code + "&grant_type=authorization_code";
        
        Map<String, Object> response = restTemplate.getForObject(url, HashMap.class);
        if (response != null && response.containsKey("openid")) {
            return (String) response.get("openid");
        }
        return null;
    }
}