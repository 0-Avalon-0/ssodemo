package com.demo.client.ClientSession;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lsh
 * @param
 * @description
 * @date 10/23/2020 4:15 PM
 */
public enum SessionStorage {
    Session_Instance;
    //token-session
    Map<String, HttpSession> sessionMap = new HashMap<>();

    public HttpSession remove(String token){
        if(sessionMap.get(token)!=null){
            HttpSession session = sessionMap.get(token);
            sessionMap.remove(token);
            return session;
        }
        return null;
    }

    public void insert(String key, HttpSession session){
        sessionMap.put(key, session);
    }

}
