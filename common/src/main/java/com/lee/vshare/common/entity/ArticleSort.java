package com.lee.vshare.common.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "tbl_article_sort")
public class ArticleSort implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 分类id
     */
    @Column(name = "sort_id")
    private Long sortId;

    /**
     * 文章id
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
    @Column(name = "modified_by")
    private Date modifiedBy;

    /**
     * 表示当前数据是否有效，默认为1有效，0则无效
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
     * 获取分类id
     *
     * @return sort_id - 分类id
     */
    public Long getSortId() {
        return sortId;
    }

    /**
     * 设置分类id
     *
     * @param sortId 分类id
     */
    public void setSortId(Long sortId) {
        this.sortId = sortId;
    }

    /**
     * 获取文章id
     *
     * @return article_id - 文章id
     */
    public Long getArticleId() {
        return articleId;
    }

    /**
     * 设置文章id
     *
     * @param articleId 文章id
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
     * @return modified_by - 更新时间
     */
    public Date getModifiedBy() {
        return modifiedBy;
    }

    /**
     * 设置更新时间
     *
     * @param modifiedBy 更新时间
     */
    public void setModifiedBy(Date modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     * 获取表示当前数据是否有效，默认为1有效，0则无效
     *
     * @return is_effective - 表示当前数据是否有效，默认为1有效，0则无效
     */
    public Boolean getIsEffective() {
        return isEffective;
    }

    /**
     * 设置表示当前数据是否有效，默认为1有效，0则无效
     *
     * @param isEffective 表示当前数据是否有效，默认为1有效，0则无效
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
        sb.append(", sortId=").append(sortId);
        sb.append(", articleId=").append(articleId);
        sb.append(", createBy=").append(createBy);
        sb.append(", modifiedBy=").append(modifiedBy);
        sb.append(", isEffective=").append(isEffective);
        sb.append("]");
        return sb.toString();
    }
}