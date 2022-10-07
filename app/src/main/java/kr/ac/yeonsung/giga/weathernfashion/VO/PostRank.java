package kr.ac.yeonsung.giga.weathernfashion.VO;

public class PostRank {
    String image;
    String rank;

    public PostRank(String image, String rank) {
        this.image = image;
        this.rank = rank;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
