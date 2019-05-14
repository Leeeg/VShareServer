package com.lee.vshare.common.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "tbl_article_content")
public class ArticleContent implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 对应文章ID
     */
    @Column(name = "article_id")
    private Long articleId;

    /**
     * 创建时间
     */
    @Column(name = "create_by")
    private Date createBy;

    /**
     * 更新时间
     */
    @Column(name = "modifield_by")
    private Date modifieldBy;

    private String content;

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
     * 获取对应文章ID
     *
     * @return article_id - 对应文章ID
     */
    public Long getArticleId() {
        return articleId;
    }

    /**
     * 设置对应文章ID
     *
     * @param articleId 对应文章ID
     */
    public void setArticleId(Long articleId) {
        this.articleId = articleId;
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
     * 获取更新时间
     *
     * @return modifield_by - 更新时间
     */
    public Date getModifieldBy() {
        return modifieldBy;
    }

    /**
     * 设置更新时间
     *
     * @param modifieldBy 更新时间
     */
    public void setModifieldBy(Date modifieldBy) {
        this.modifieldBy = modifieldBy;
    }

    /**
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", articleId=").append(articleId);
        sb.append(", createBy=").append(createBy);
        sb.append(", modifieldBy=").append(modifieldBy);
        sb.append(", content=").append(content);
        sb.append("]");
        return sb.toString();
    }
}