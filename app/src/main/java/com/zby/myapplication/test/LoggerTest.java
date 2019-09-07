package com.zby.myapplication.test;

import com.zby.util.Logger;
import com.zby.util.TimeUtil;

import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * author ZhuBingYang
 * date   2019-09-06
 */
public class LoggerTest implements Runnable {
    private static final String TAG = "LoggerTest";

    @Override
    public void run() {
        Logger.D.list("list", Arrays.asList("asd", "add", "asf"));

        Map<String, Entity> map = new HashMap<>();
        map.put("hehe", new Entity("hehehe", "he"));
        map.put("haha", new Entity("hahahah", "ha"));
        map.put("xixi", new Entity("xixixi", "xi"));

        Logger.W.map(null, map);

        Logger.E.json(TAG, "{\"quiz\":{\"sport\":{\"q1\":{\"question\":\"Which one is correct team name in NBA?\",\"options\":[\"New York Bulls\",\"Los Angeles Kings\",\"Golden State Warriros\",\"Huston Rocket\"],\"answer\":\"Huston Rocket\"}},\"maths\":{\"q1\":{\"question\":\"5 + 7 = ?\",\"options\":[\"10\",\"11\",\"12\",\"13\"],\"answer\":\"12\"},\"q2\":{\"question\":\"12 - 8 = ?\",\"options\":[\"1\",\"2\",\"3\",\"4\"],\"answer\":\"4\"}}}}");
        Logger.E.json(null, map.get("haha").toString());

        try {
            Logger.D.log(TAG, String.valueOf(TimeUtil.parseDate("2009-02-14 07:31:31", TimeUtil.YMDHMS).getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
