package com.example.zhwh;

import java.util.ArrayList;

/**
 * Created by 毛琦 on 2016/7/29.
 */
public class NewsMenu {
    public int retcode;
    public ArrayList<Integer> extend;
    public ArrayList<NewsMenuData> data;

    @Override
    public String toString() {
        return "NewsMenu{" +
                "data=" + data +
                ", retcode=" + retcode +
                ", extend=" + extend +
                '}';
    }

    public class NewsMenuData{
        public int id;
        public String title;
        int type;
        public ArrayList<NewsTitleData> children;

        @Override
        public String toString() {
            return "NewsMenuData{" +
                    "children=" + children +
                    ", id=" + id +
                    ", title='" + title + '\'' +
                    ", type=" + type +
                    '}';
        }

    }

    public class NewsTitleData {
        public int id;
        public String title;
        int type;
        public String url;

        @Override
        public String toString() {
            return "NewsTitleData{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", type=" + type +
                    '}';
        }
    }
}
