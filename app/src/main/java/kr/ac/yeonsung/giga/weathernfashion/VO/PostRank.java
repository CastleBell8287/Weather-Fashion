package kr.ac.yeonsung.giga.weathernfashion.VO;

public class PostRank {
    String image;
    String title;
    String max_temp;
    String min_temp;

    public void setMax_temp(String max_temp) {
        this.max_temp = max_temp;
    }

    public void setMin_temp(String min_temp) {
        this.min_temp = min_temp;
    }

    public String getMax_temp() {
        return max_temp;
    }

    public String getMin_temp() {
        return min_temp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PostRank(String image, String title ,String max_temp, String min_temp) {
        this.image = image;
        this.title = title;
        this.max_temp = max_temp;
        this.min_temp = min_temp;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
