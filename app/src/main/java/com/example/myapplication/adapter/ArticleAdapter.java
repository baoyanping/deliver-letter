package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.bean.ArticleBean;

import java.util.List;
//ArticleAdapter从RecyclerView.Adapter中派生，ArticleHolder作为泛型参数，使用的是RecyclerView中的Adapter类
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleHolder> {
    private List<ArticleBean> list;
    private Context context;
    private ClickDataListener<ArticleBean> listener;

    public ArticleAdapter(List<ArticleBean> list, Context context) {
        this.list = list;//访问list成员方法
        this.context = context;
    }//有函数得构成函数方法，传递两个参数，传进来的参数是采用this.list=list的方式添加的

    @NonNull
    @Override//子类重写
    //onCreateViewHolder得实现，也就是说显示一条文本，先创建了一个条目控件类型
    public ArticleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_article,parent,false);
        //Adapter的getView中经常用到此方法，将xml转换为view对象，item需要获得parent的layoutparams来给自己定位属性
        return new ArticleHolder(view);//传入到ArticleHolder中
    }

    @Override
    //为每行设置不同的数据，从前面传入的Article中取出view
    public void onBindViewHolder(@NonNull ArticleHolder holder, int position) {
        ArticleBean bean = list.get(position);//创建一个Articlebean类的实例名为bean,再调用List的get（）方法将List中下标为positon的对象赋给bean
        holder.title.setText(bean.getTitle());
        holder.content.setText(bean.getContent());
        holder.author.setText(bean.getAuthorName());//直接设置内容，链式调用
        String approval = "";
        switch (bean.getApproval()) {//根据参数position来确定第几行，不同的行设置不同的文本
            case 0:
                approval = "未审批";
                break;
            case 1:
                approval = "已通过";
                break;
            case 2:
                approval = "未通过";
                break;
        }
        holder.approval.setText(approval);//设置显示
        if (listener != null)
            holder.itemView.setOnClickListener(v -> {
                listener.onClick(bean, position);
                //监听器，如果设置了回调，则设置点击事件
            });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }//返回行数

    public void setOnItemClickListener(ClickDataListener listener) {
        this.listener = listener;
    }
    //新建变量用于保存用户设置的监听器
    public void refreshAllData() {
        notifyDataSetChanged();
    }
    //刷新数据方法
    public void refreshPositionData(int position) {
        notifyItemChanged(position);
    }
    //更新列表position位置上的数据，可以调用
    public void notifyItemRemove(int position) {
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, list.size() - position);
    }
    //列表position位置移除一条数据时调用，伴有动画效果
    public void addAllData(List<ArticleBean> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }
    //先定义一个ArticleHolder子类
    static class ArticleHolder extends RecyclerView.ViewHolder {
        TextView title, content, author, approval;

        public ArticleHolder(@NonNull View itemView) {
            super(itemView);//被继承的类，在子类的构造方法中调用父类有参构造方法
            title = itemView.findViewById(R.id.ia_tv_title);//使用此方法时会调用ViewGroup中的findViewTraversal
            content = itemView.findViewById(R.id.ia_tv_content);
            //布局文件中所有组件的对象封装到ViewHolder对象中
            author = itemView.findViewById(R.id.ia_tv_author);
            approval = itemView.findViewById(R.id.ia_tv_approval);
        }
    }
}
