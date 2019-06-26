package me.zhoudongyu.pojo.vo;

/**
 * @author Steve
 * @date 2019/06/26
 */
public class PublishVideo {

    public UsersVO Publisher;
    public boolean userLikeVideo;

    public UsersVO getPublisher() {
        return Publisher;
    }

    public void setPublisher(UsersVO publisher) {
        Publisher = publisher;
    }

    public boolean isUserLikeVideo() {
        return userLikeVideo;
    }

    public void setUserLikeVideo(boolean userLikeVideo) {
        this.userLikeVideo = userLikeVideo;
    }
}
