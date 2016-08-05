package com.example.zhwh;

import java.util.ArrayList;

/**
 * Created by 毛琦 on 2016/8/2.
 */
public class TopNews {
    public NewsList data;

    @Override
    public String toString() {
        return "TopNews{" +
                "data=" + data +
                '}';
    }

    public class NewsList{
        public String countcommenturl;
        public String more;
        public String title;
        public ArrayList<NewsItem> news;
        public ArrayList<OtherTopImage> topic;
        public ArrayList<TopImage> topnews;

        @Override
        public String toString() {
            return "NewsList{" +
                    "countcommenturl='" + countcommenturl + '\'' +
                    ", more='" + more + '\'' +
                    ", title='" + title + '\'' +
                    ", news=" + news +
                    ", topic=" + topic +
                    ", topnews=" + topnews +
                    '}';
        }
    }
    public class NewsItem{
        public int id;
        public String listimage;
        public String pubdate;
        public String title;
        public String type;
        public String url;

        @Override
        public String toString() {
            return "NewsItem{" +
                    "id=" + id +
                    ", listimage='" + listimage + '\'' +
                    ", pubdate='" + pubdate + '\'' +
                    ", title='" + title + '\'' +
                    ", type='" + type + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }

    public class OtherTopImage{
        public String description;
        public String listimage;
        public String title;
        public String url;

        @Override
        public String toString() {
            return "OtherTopImage{" +
                    "description='" + description + '\'' +
                    ", listimage='" + listimage + '\'' +
                    ", title='" + title + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }

    public class TopImage{
        public int id;
        public String topimage;
        public String pubdate;
        public String title;
        public String type;
        public String url;

        @Override
        public String toString() {
            return "TopImage{" +
                    "id=" + id +
                    ", topimage='" + topimage + '\'' +
                    ", pubdate='" + pubdate + '\'' +
                    ", title='" + title + '\'' +
                    ", type='" + type + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }
}
