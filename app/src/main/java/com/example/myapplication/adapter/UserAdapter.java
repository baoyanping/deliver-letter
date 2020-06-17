package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.bean.UserBean;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {

    private List<UserBean> list;
    private Context context;
    private ClickDataListener<UserBean> listener;

    public UserAdapter(List<UserBean> list, Context context) {
        this.list = list;
        this.context = context;
    }
//构造方法，输入数据
    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new UserHolder(view);
    }
//创建viewHoleder ,返回每一项布局
    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, final int position) {
        final UserBean bean = list.get(position);
        holder.name.setText(bean.getUsername());
        holder.account.setText(bean.getAccount());//将数据和控件绑定
        String identity = "";
        switch (bean.getIdentity()) {
            case 1:
                identity = "管理员";
                break;
            case 2:
                identity = "专家";
                identity = "作家";
                break;
        }
        holder.identity.setText(identity);
        String approval = "";
        switch (bean.getApproval()) {
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
        holder.approval.setText(approval);
        if (listener !=                       null) {
            holder.itemView.setOnClickListener(v -> {
                listener.onClick(bean, position);
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void setOnItemClickListener(ClickDataListener listener) {
        this.listener = listener;
    }

    public void refreshAllData() {
        notifyDataSetChanged();
    }

    public void refreshPositionData(int position) {
        notifyItemChanged(position);
    }

    public void notifyItemRemove(int position) {
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, list.size() - position);
    }

    public void addAllData(List<UserBean> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    static class UserHolder extends RecyclerView.ViewHolder {
        TextView name, account, identity, approval;
        View view;

        public UserHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.iu_tv_name);
            account = itemView.findViewById(R.id.iu_tv_account);
            identity = itemView.findViewById(R.id.iu_tv_identity);
            approval = itemView.findViewById(R.id.iu_tv_approval);
        }
    }
}
