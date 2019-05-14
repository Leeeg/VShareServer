package com.lee.vshare.common.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "tbl_article_message")
public class ArticleMessage implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 文章ID
     */
    @Column(name = "article_id")
    private Long articleId;

    /**
     * 对应的留言ID
     */
    @Column(name = "message_id")
    private Long messageId;

    /**
     * 创建时间
     */
    @Column(name = "create_by")
    private Date createBy;

    /**
     * 是否有效，默认为1有效，置0无效
     */
    @Column(name = "is_effective")
    private Boolean isEffective;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取文章ID
     *
     * @return article_id - 文章ID
     */
    public Long getArticleId() {
        return articleId;
    }

    /**
     * 设置文章ID
     *
     * @param articleId 文章ID
     */
    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    /**
     * 获取对应的留言ID
     *
     * @return message_id - 对应的留言ID
     */
    public Long getMessageId() {
        return messageId;
    }

    /**
     * 设置对应的留言ID
     *
     * @param messageId 对应的留言ID
     */
    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    /**
     * 获取创建时间
     *
     * @return create_by - 创建时间
     */
    public Date getCreateBy() {
        return createBy;
    }

    /**
     * 设置创建时间
     *
     * @param createBy 创建时间
     */
    public void setCreateBy(Date createBy) {
        this.createBy = createBy;
    }

    /**
     * 获取是否有效，默认为1有效，置0无效
     *
     * @return is_effective - 是否有效，默认为1有效，置0无效
     */
    public Boolean getIsEffective() {
        return isEffective;
    }

    /**
     * 设置是否有效，默认为1有效，置0无效
     *
     * @param isEffective 是否有效，默认为1有效，置0无效
     */
    public void setIsEffective(Boolean isEffective) {
        this.isEffective = isEffective;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", articleId=").append(articleId);
        sb.append(", messageId=").append(messageId);
        sb.append(", createBy=").append(createBy);
        sb.append(", isEffective=").append(isEffective);
        sb.append("]");
        return sb.toString();
    }
}