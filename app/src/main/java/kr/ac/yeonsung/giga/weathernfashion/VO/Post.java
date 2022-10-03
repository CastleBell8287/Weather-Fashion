package kr.ac.yeonsung.giga.weathernfashion.VO;

import java.util.ArrayList;
import java.util.HashMap;

public class Post {
    private String post_id;
    private String post_content;
    private String post_image;
    private String user_email;
    private Long min_temp;
    private Long max_temp;
    private int likeCount = 0;
    private HashMap<String, Boolean> likes = new HashMap<>();
    private ArrayList categoris;

    public Post(String post_id, String post_content, String post_image, String user_email, Long min_temp,
                Long max_temp , int likeCount, HashMap<String, Boolean> likes, ArrayList categoris){
        this.post_id = post_id;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getPost_content() {
        return post_content;
    }

    public void setPost_content(String post_content) {
        this.post_content = post_content;
    }

    public String getPost_image() {
        return post_image;
    }

    public void setPost_image(String post_image) {
        this.post_image = post_image;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public Long getMin_temp() {
        return min_temp;
    }

    public void setMin_temp(Long min_temp) {
        this.min_temp = min_temp;
    }

    public Long getMax_temp() {
        return max_temp;
    }

    public void setMax_temp(Long max_temp) {
        this.max_temp = max_temp;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public HashMap<String, Boolean> getLikes() {
        return likes;
    }

    public void setLikes(HashMap<String, Boolean> likes) {
        this.likes = likes;
    }

    public ArrayList getCategoris() {
        return categoris;
    }

    public void setCategoris(ArrayList categoris) {
        this.categoris = categoris;
    }
}