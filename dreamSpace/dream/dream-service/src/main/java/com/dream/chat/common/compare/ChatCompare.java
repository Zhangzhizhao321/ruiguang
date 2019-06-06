package com.dream.chat.common.compare;

import java.util.Comparator;
import java.util.List;

/**
 * <p>
 * 功能描述
 * <p>
 *
 * @author yangjing
 * @since 2018-12-29
 */
public class ChatCompare {
   /* public List<ChatResVo> comparatorListByTime(List<ChatResVo> list) {
        list.sort(new Comparator<ChatResVo>() {
            @Override
            public int compare(ChatResVo o1, ChatResVo o2) {
                return o2.getLastTime().compareTo(o1.getLastTime());
            }
        });
        return list;
    }

    public List<ChatResVo> comparatorListByTimeAsc(List<ChatResVo> list) {
        list.sort(new Comparator<ChatResVo>() {
            @Override
            public int compare(ChatResVo o1, ChatResVo o2) {
                if (o1.getChatTime().compareTo(o2.getChatTime()) < 0) {
                    return -1;
                } else if (o1.getChatTime().compareTo(o2.getChatTime()) > 0) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        return list;
    }*/
}
