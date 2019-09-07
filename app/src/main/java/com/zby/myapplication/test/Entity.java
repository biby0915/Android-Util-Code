package com.zby.myapplication.test;

/**
 * @author ZhuBingYang
 * @date 2019-08-27
 */
public class Entity {
    private String key;
    private String value;

    public Entity(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
