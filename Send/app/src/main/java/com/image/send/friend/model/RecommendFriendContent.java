/*
 * Copyright (c) 2015. <Chungseok Baek>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.image.send.friend.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by BAEK on 9/9/2015.
 */
@SuppressWarnings("ClassNamePrefixedWithPackageName")
public class RecommendFriendContent {

    /**
     * List
     */
    public static List<RecommendFriend> ITEMS = new ArrayList<>();

    /**
     * Map, key by username
     */
    public static Map<String,RecommendFriend> ITEM_MAP = new HashMap<>();

    private static void addItem(RecommendFriend item) {
        ITEM_MAP.put(item.name, item);
        // to defeat the case that saves the same instance as different list items.
        if(ITEM_MAP.size()  == ITEMS.size() +1){
            ITEMS.add(item);
        }
    }

    public static void addRecommendFriendInstance(String name){
        addItem(new RecommendFriend(name));
    }

    static{
        addItem(new RecommendFriend("rc_ex_1"));
        addItem(new RecommendFriend("rc_ex_2"));
        addItem(new RecommendFriend("rc_ex_3"));
    }


    public static class RecommendFriend{
        String name;
        String syncedAt;

        public RecommendFriend(String name){
            this.name = name;
        }
        @Override
        public String toString(){
            return name;
        }
    }
}