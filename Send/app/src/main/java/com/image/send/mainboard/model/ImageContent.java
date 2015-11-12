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

package com.image.send.mainboard.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by BAEK on 9/11/2015.
 */
public class ImageContent {

    /**
     * List
     */
    public static List<Image> ITEMS = new ArrayList<>();

    /**
     * Map, key by username
     */
    public static Map<Integer,Image> ITEM_MAP = new HashMap<>();

    public static void addItem(Image item) {
        ITEM_MAP.put(item.id, item);
        // to defeat the case that saves the same instance as different list items.
        if(ITEM_MAP.size()  == ITEMS.size() +1){
            ITEMS.add(item);
        } else{
            Log.d("err", "Content system got error");
        }
    }
    private static void deleteItem(Image img){
        if(img != null) {
            Image i_to_remove = ITEM_MAP.get(img.id);
            ITEMS.remove(img);
            ITEM_MAP.remove(img.id);
        }
    }

    public static void addImageInstance(String name){
        addItem(new Image(name));
    }

    public static void clear() {
        ITEM_MAP.clear();
        ITEMS.clear();
    }




    public static class Image{
        private static int staticId = 1;

        public int getId() {
            return id;
        }

        private int id;

        public void setName(String name) {
            this.name = name;
        }

        String name;
        String syncedAt;

        public Image(){
            id = getNextId();
        }

        public Image(String name){
            id = getNextId();
            this.name = name;
        }

        private synchronized int getNextId(){
            return staticId++;
        }

        @Override
        public String toString(){
            return name;
        }

        public String getName() {
            return name;
        }
    }
}
