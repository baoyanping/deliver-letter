package com.example.myapplication.bean;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "article",foreignKeys = {@ForeignKey(entity = UserBean.class,parentColumns = "id",childColumns ="authorId"
,onDelete = ForeignKey.SET_NULL)})

public class ArticleBean implements Serializable {
    @PrimaryKey(autoGenerate = true)//主键注解，autoGenerate为自增长
    private int id;

    private String title;       //标题
    private String content;     //内容

    private String authorName;
    private int authorId;       //作者id
    private String writeTime;   //作者提审时间


    private String expertName;
    private int expertId;       //专家id
    private String examineTime; //审核时间
    private int approval;       //是否审批  0未审批 1已通过 2未通过
    private String remarks;     //未通过的备注

    public ArticleBean(String title, String content, int authorId, String writeTime,int approval) {
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.writeTime = writeTime;
        this.approval=approval;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getWriteTime() {
        return writeTime;
    }

    public void setWriteTime(String writeTime) {
        this.writeTime = writeTime;
    }

    public int getExpertId() {
        return expertId;
    }

    public void setExpertId(int expertId) {
        this.expertId = expertId;
    }

    public String getExamineTime() {
        return examineTime;
    }

    public void setExamineTime(String examineTime) {
        this.examineTime = examineTime;
    }

    public int getApproval() {
        return approval;
    }

    public void setApproval(int approval) {
        this.approval = approval;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getExpertName() {
        return expertName;
    }

    public void setExpertName(String expertName) {
        this.expertName = expertName;
    }

    @Override
    public String toString() {
        return "ArticleBean{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", authorName='" + authorName + '\'' +
                ", authorId=" + authorId +
                ", writeTime='" + writeTime + '\'' +
                ", expertName='" + expertName + '\'' +
                ", expertId=" + expertId +
                ", examineTime='" + examineTime + '\'' +
                ", approval=" + approval +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
