package com.example.myapplication.manager.bd;

import com.example.myapplication.bean.ArticleBean;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ArticleDao {

    @Insert
    long insertArticle(ArticleBean bean);

    @Delete
    int deleteArticle(ArticleBean bean);

    @Query("SELECT * FROM article")
    List<ArticleBean> queryAll();

    /**
     * 查询作者自己的
     *
     * @param id 作者自己的ID
     */
    @Query("SELECT * FROM article where authorId =:id")
    List<ArticleBean> queryOneAuthor(int id);

    /**
     * 查询未审核的
     */
    @Query("SELECT a.username AS authorName, article.* FROM article left join subscriber as a on a.id = article.authorId  where article.approval =0")
    List<ArticleBean> queryNoApproval();

    @Update
    int updataArticel(ArticleBean bean);

    /**
     * 查询所有书籍
     */
    @Query("select a.username AS authorName,b.username as expertName,article.* from article left join subscriber  a on a.id = article.authorId left join subscriber b on b.id = article.expertId")
    List<ArticleBean> queryManagerAll();

}