package com.example.springsecuritybase.social.qq;

/**
 * summary.
 * <p>
 * detailed description
 *
 * @author Mengday Zhang
 * @version 1.0
 * @since 2019-05-19
 */
public class Main {
    public static void main(String[] args) {
        String result = "callback({\"client_id\":\"100550231\", \"openid\":\"123456\"})";
        String[] items = result.split("openid");
        String openid = items[1].substring(3, items[1].length() - 3);
        System.out.println(openid);
    }
}
