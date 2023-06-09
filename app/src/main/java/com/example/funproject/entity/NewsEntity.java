package com.example.funproject.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author: wei
 * @date: 2020-08-01
 **/

public class NewsEntity implements Serializable {
    private int newsId;
    private String newsTitle;
    private String authorName;
    private String headerUrl;
    private int commentCount;
    private String releaseDate;
    private int type;
    private List<ThumbEntitiesBean> thumbEntities;

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getHeaderUrl() {
        return headerUrl;
    }

    public void setHeaderUrl(String headerUrl) {
        this.headerUrl = headerUrl;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<ThumbEntitiesBean> getThumbEntities() {
        return thumbEntities;
    }

    public void setThumbEntities(List<ThumbEntitiesBean> thumbEntities) {
        this.thumbEntities = thumbEntities;
    }

    public static class ThumbEntitiesBean {
        /**
         * thumbId : 1
         * thumbUrl : http://p1-tt.byteimg.com/large/pgc-image/S6KR5958Y5X2Qt?from=pc
         * newsId : 1
         */

        private int thumbId;
        private String thumbUrl;
        private int newsId;

        public int getThumbId() {
            return thumbId;
        }

        public void setThumbId(int thumbId) {
            this.thumbId = thumbId;
        }

        public String getThumbUrl() {
            return thumbUrl;
        }

        public void setThumbUrl(String thumbUrl) {
            this.thumbUrl = thumbUrl;
        }

        public int getNewsId() {
            return newsId;
        }

        public void setNewsId(int newsId) {
            this.newsId = newsId;
        }
    }
}
