package com.jidouauto.market.module.lib.common.http;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.gson.Gson;
import com.jidouauto.market.module.lib.common.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

/**
 * @author Yin, Lei
 * @date 2018/9/28 10:10
 */
public class JDOSignUtil {

    /**
     * API秘钥，不同API更换不同的KEY
     */
    public static final String SIGN_KEY = "0FDE05C42CD0F318D913B1F5A18BCD30";

//    private static final String SIGN_KEY = "9FAC0FF6BD6825B5728CB247CBDE1104";

    public static final String POST_METHOD = "POST";

    public static final String GET_METHOD = "GET";

    /**
     * 获取签名
     *
     * @param requestParams 请求参数，不包含sign
     * @param key           签名秘钥
     * @return
     */
    public static String getSignStr(String requestParams, String key) {

        String queryStr = getSortedStr(JSONObject.parseObject(requestParams, new TypeReference<Map<String, String>>() {
        }));
        //加上key
        String unsignStr = queryStr + "&key=" + key;
        return MD5Utils.getMD5(unsignStr).toUpperCase();
    }

    /**
     * 验证签名
     *
     * @param params 请求的所有参数参数
     * @return
     */
    public static boolean verifySign(String params, String method) throws IOException {
        Gson gson = new Gson();
        Map<String, String> paramMap = null;
        if (POST_METHOD.equalsIgnoreCase(method)) {
            paramMap = JSONObject.parseObject(params, new TypeReference<Map<String, String>>() {
            });
        } else if (GET_METHOD.equalsIgnoreCase(method)) {
            paramMap = getMapData(params);
        }
        String sign = (String) paramMap.get("sign");
        paramMap.remove("sign");
        String paramSign = getSignStr(gson.toJson(paramMap), SIGN_KEY);
        return sign.equals(paramSign);
    }

    /**
     * 获取随机字符串
     *
     * @param length 生成字符串的长度
     * @return
     */
    public static String getNonceStr(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int num = random.nextInt(62);
            buf.append(str.charAt(num));
        }
        return buf.toString();
    }

    /**
     * query string 转 map
     *
     * @param getStr get请求query string
     * @return
     */
    private static Map<String, String> getMapData(String getStr) throws UnsupportedEncodingException {
        String[] strs = getStr.split("&");
        HashMap<String, String> dataMap = new HashMap<>(16);
        for (int i = 0; i < strs.length; i++) {
            String[] str = strs[i].split("=");
            dataMap.put(str[0], URLDecoder.decode(str[1], "utf-8"));
        }
        return dataMap;
    }

    /**
     * Map排序去除空值并拼接为query string
     *
     * @param unSortedMap
     * @return
     */
    public static String getSortedStr(Map<String, String> unSortedMap) {
//        String sortedStr = unSortedMap
//                .entrySet()
//                .stream()
//                .filter(entry -> !StringUtils.isEmpty(entry.getValue()))
//                .sorted(Map.Entry.comparingByKey())
//                .map(entry -> {
//                    return entry.getKey() + "=" + entry.getValue();
//
//                })
//                .collect(Collectors.joining("&"));
//
//        return sortedStr;
        Map<String, String> resultMap = new HashMap<>();
        for (Map.Entry<String, String> entry : unSortedMap.entrySet()) {
            if (!StringUtils.isEmpty(entry.getValue())) {
                resultMap.put(entry.getKey(), entry.getValue());
            }
        }

        Map<String, String> treeMap = new TreeMap<>(resultMap);

        StringBuilder resultStr = new StringBuilder();
        String and = "";
        for (Map.Entry<String, String> entry : treeMap.entrySet()) {
            resultStr.append(and).append(entry.getKey()).append("=").append(entry.getValue());
            and = "&";
        }
        return resultStr.toString();
    }

    public static void main(String[] args) throws Exception {
//        System.out.println(getNonceStr(6));
//        Gson gson = new Gson();
//        Map map = new HashMap();
//        map.put("token","eyJraWQiOiI5NTdiZTE2M2ViNzE0MTY5OTAxYTRlNjhhZDk1MWRmMyIsInR5cCI6IkpXVCIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiIyOTY5YjYyMzMyNDQ0NzllOTQwMTVlY2M5MTg5NzdhMCIsImlzcyI6IkpETyIsInZpbiI6Ind4IiwiZXhwIjoxNTM5OTQ4ODMyLCJpYXQiOjE1Mzk4NjI0MzJ9.A5BT2bHnHgevYpyOSFvesUXt3_Z-OroDoQyVndHAOlSvb1nO-UJOOCQ7DUh_Vpud721T9v4_qaIAIdJcMHgRsCk6vdpafxhb2yne2-i5uwZnHSLaYnSSM7r8iSX0BQn6-PXYtHbNJOocL_zJYndyh9Wf7KpbW-r6vIklT16qHNmjOVM8rScki7kUKrZyG6QNYa3MTiq9qBThx4XcpJJxBOUQevNBnPD7tUJZ9LeFt_EBjYvtCciwcvlZfdlj72TFzQ2YYpe47E4geG7FZt6IEufN69RpSsU_RuXx5sTsN-U_TAkuF-9dq6oNnxQet1Kan0TWRbJI0f-OrmPO9icanw");
//        map.put("ts",1539934567L);
//        map.put("nonceStr","7SjOe9HX1");
//        map.put("channel","audi");
//        map.put("versionId",6);
//        map.put("rate",4);
//        map.put("content","6666666");
//        System.out.println(getSignStr(gson.toJson(map), SIGN_KEY));
//        map.put("sign", getSignStr(gson.toJson(map), SIGN_KEY));
//        System.out.println(JSONObject.toJSONString(map));
//        System.out.println(verifySign("aa=1&zz=as%26%3D&cc=33&sign=833CACFDE09FB823996FCB84BF48D08A", "get"));

        String s = "{\n" +
                "    \"versionId\":13,\n" +
                "    \"rate\":4,\n" +
                "    \"channel\":\"audi\",\n" +
                "    \"nonceStr\":\"7SjOe9HX1\",\n" +
                "    \"content\":\"6666666\",\n" +
                "    \"token\":\"eyJraWQiOiI5NTdiZTE2M2ViNzE0MTY5OTAxYTRlNjhhZDk1MWRmMyIsInR5cCI6IkpXVCIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiIyOTY5YjYyMzMyNDQ0NzllOTQwMTVlY2M5MTg5NzdhMCIsImlzcyI6IkpETyIsInZpbiI6Ind4IiwiZXhwIjoxNTM5OTQ4ODMyLCJpYXQiOjE1Mzk4NjI0MzJ9.A5BT2bHnHgevYpyOSFvesUXt3_Z-OroDoQyVndHAOlSvb1nO-UJOOCQ7DUh_Vpud721T9v4_qaIAIdJcMHgRsCk6vdpafxhb2yne2-i5uwZnHSLaYnSSM7r8iSX0BQn6-PXYtHbNJOocL_zJYndyh9Wf7KpbW-r6vIklT16qHNmjOVM8rScki7kUKrZyG6QNYa3MTiq9qBThx4XcpJJxBOUQevNBnPD7tUJZ9LeFt_EBjYvtCciwcvlZfdlj72TFzQ2YYpe47E4geG7FZt6IEufN69RpSsU_RuXx5sTsN-U_TAkuF-9dq6oNnxQet1Kan0TWRbJI0f-OrmPO9icanw\",\n" +
                "    \"ts\":1540439029\n" +
                "}";
        System.out.println(getSignStr(s, JDOSignUtil.SIGN_KEY));
    }

}
