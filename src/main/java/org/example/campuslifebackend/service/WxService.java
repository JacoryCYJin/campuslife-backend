package org.example.campuslifebackend.service;

/**
 * 微信服务接口
 */
public interface WxService {
    /**
     * 通过code获取微信openid
     * @param code 微信授权code
     * @return 微信openid
     */
    String getWxOpenid(String code);
}