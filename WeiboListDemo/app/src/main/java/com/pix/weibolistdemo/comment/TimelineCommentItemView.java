package com.pix.weibolistdemo.comment;

import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pix.weibolistdemo.R;
import com.pix.weibolistdemo.base.BizFragment;
import com.pix.weibolistdemo.bean.StatusComment;
import com.pix.weibolistdemo.bean.WeiBoUser;
import com.pix.weibolistdemo.utils.AisenUtils;
import com.pix.weibolistdemo.utils.ImageConfigUtils;
import com.pix.weibolistdemo.widget.AisenTextView;

import org.aisen.android.common.utils.Utils;
import org.aisen.android.component.bitmaploader.BitmapLoader;
import org.aisen.android.support.inject.ViewInject;
import org.aisen.android.ui.fragment.adapter.ARecycleViewItemView;

/**
 * Created by wangdan on 16/1/8.
 */
public class TimelineCommentItemView extends ARecycleViewItemView<StatusComment> {

    public static final int LAYOUT_RES = R.layout.item_timeline_comment;

    @ViewInject(id = R.id.imgPhoto)
    ImageView imgPhoto;
    @ViewInject(id = R.id.txtName)
    TextView txtName;
    @ViewInject(id = R.id.txtDesc)
    TextView txtDesc;
    @ViewInject(id = R.id.txtContent)
    AisenTextView txtContent;

    private TimelineCommentFragment mFragment;
    private BizFragment bizFragment;
    int firstTop;
    int normalTop;

    public TimelineCommentItemView(TimelineCommentFragment fragment, View itemView) {
        super(fragment.getActivity(), itemView);

        this.mFragment = fragment;
        bizFragment = BizFragment.createBizFragment(fragment);

        firstTop = Utils.dip2px(getContext(), 16);
        normalTop = Utils.dip2px(getContext(), 8);
    }

    @Override
    public void onBindData(View convertView, StatusComment data, int position) {
        WeiBoUser user = data.getUser();
        if (user != null) {
            BitmapLoader.getInstance().display(mFragment,
                                                    AisenUtils.getUserPhoto(user),
                                                    imgPhoto, ImageConfigUtils.getLargePhotoConfig());
            bizFragment.userShow(imgPhoto, user);
            txtName.setText(AisenUtils.getUserScreenName(user));
        }
        else {
            bizFragment.userShow(imgPhoto, null);
            txtName.setText(R.string.error_cmts);
            imgPhoto.setImageResource(R.drawable.user_placeholder);
        }

        txtContent.setContent(data.getText());
        AisenUtils.setTextSize(txtContent);

        String createAt = AisenUtils.convDate(data.getCreated_at());
        String from = String.format("%s", Html.fromHtml(data.getSource()));
        String desc = String.format("%s %s", createAt, from);
        txtDesc.setText(desc);

        int top = position == 0 ? firstTop : normalTop;
        convertView.setPadding(convertView.getPaddingLeft(), top, convertView.getPaddingRight(), convertView.getPaddingBottom());
    }

}
