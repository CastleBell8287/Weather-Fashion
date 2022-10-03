package kr.ac.yeonsung.giga.weathernfashion.VO;

public class BoardRank {
    int image;
    String rank;

    public BoardRank(int image, String rank) {
        this.image = image;
        this.rank = rank;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
