package me.zhoudongyu.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "users_report")
@ApiModel(value = "用户举报实体", description = "用户举报实体")
public class UsersReport {

    @ApiModelProperty(hidden = true)
    @Id
    private String id;

    /**
     * 被举报用户id
     */
    @ApiModelProperty(value = "被举报用户id", name = "dealUserId", example = "180518CKMAAM5TXP", required = true)
    @Column(name = "deal_user_id")
    private String dealUserId;

    @ApiModelProperty(value = "被举报视频id", name = "dealVideoId", example = "180426GA3SBB4DP0", required = true)
    @Column(name = "deal_video_id")
    private String dealVideoId;

    /**
     * 类型标题，让用户选择，详情见 枚举
     */
    @ApiModelProperty(value = "举报标题", name = "title", example = "涉嫌诈骗", required = true)
    private String title;

    /**
     * 内容
     */
    @ApiModelProperty(value = "举报内容", name = "content", example = "涉嫌诈骗的内容", required = true)
    private String content;

    /**
     * 举报人的id
     */
    @ApiModelProperty(value = "举报人的id", name = "userid", example = "190410H60NW3930H", required = true)
    private String userid;

    /**
     * 举报时间
     */
    @ApiModelProperty(hidden = true)
    @Column(name = "create_date")
    private Date createDate;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取被举报用户id
     *
     * @return deal_user_id - 被举报用户id
     */
    public String getDealUserId() {
        return dealUserId;
    }

    /**
     * 设置被举报用户id
     *
     * @param dealUserId 被举报用户id
     */
    public void setDealUserId(String dealUserId) {
        this.dealUserId = dealUserId;
    }

    /**
     * @return deal_video_id
     */
    public String getDealVideoId() {
        return dealVideoId;
    }

    /**
     * @param dealVideoId
     */
    public void setDealVideoId(String dealVideoId) {
        this.dealVideoId = dealVideoId;
    }

    /**
     * 获取类型标题，让用户选择，详情见 枚举
     *
     * @return title - 类型标题，让用户选择，详情见 枚举
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置类型标题，让用户选择，详情见 枚举
     *
     * @param title 类型标题，让用户选择，详情见 枚举
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取内容
     *
     * @return content - 内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置内容
     *
     * @param content 内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取举报人的id
     *
     * @return userid - 举报人的id
     */
    public String getUserid() {
        return userid;
    }

    /**
     * 设置举报人的id
     *
     * @param userid 举报人的id
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * 获取举报时间
     *
     * @return create_date - 举报时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置举报时间
     *
     * @param createDate 举报时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}