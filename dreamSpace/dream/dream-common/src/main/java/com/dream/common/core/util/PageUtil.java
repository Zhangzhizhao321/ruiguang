package com.dream.common.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 功能描述
 * <p>
 *
 * @author yangjing
 * @since 2018-11-10
 */
public class PageUtil {

    public static Map<String, Object> ListByPage(Integer pageNumber, Integer pageSize, List list) {
        if(list.size() <= 0){
            Map<String, Object> map = new HashMap<>();
            map.put("hasNextPage", false);
            map.put("pageList", null);
            return map;
        }

        if(pageNumber == null || pageNumber <= 0){
            pageNumber = 1;
        }
        if(pageSize == null || pageSize <= 0){
            pageSize = 10;
        }

        List pageList = new ArrayList();

        Integer total = (((pageNumber - 1) * pageSize) + pageSize > list.size() ? list.size()
                : ((pageNumber - 1) * pageSize) + pageSize);
        for (int i = (pageNumber - 1) * pageSize; i < total; i++) {
            pageList.add(list.get(i));
        }

        boolean hasNextPage;
        Integer totalPage = list.size() % pageSize > 0 ? (list.size() / pageSize) + 1 : (list.size() / pageSize);
            if (pageNumber >= totalPage) {
            hasNextPage = false;
        } else {
            hasNextPage = true;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("hasNextPage", hasNextPage);
        map.put("pageList", pageList);
        map.put("totalCount",list.size());
        return map;
    }
}
