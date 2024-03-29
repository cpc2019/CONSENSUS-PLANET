package com.pingan.baselibs.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.pingan.baselibs.R;

/**
 * 通用LoadingDialog
 * Created by duyuan797 on 16/11/24.
 */
public class LoadingDialog {

    private Context context;
    private Dialog dialog;
    private TextView textView;
    //弹出框view
    private View dialogView;

    private ProgressBar progressBar;

    /**
     * 构造方法
     */
    public LoadingDialog(Context context) {
        this.context = context;
        dialog = new Dialog(context, R.style.common_loading_dialog);
        dialog.setContentView(R.layout.common_loading);
        textView = dialog.findViewById(R.id.common_dialog_loading_text);
        dialogView = dialog.findViewById(R.id.common_dialog_loading_relativelayout);
        progressBar = dialog.findViewById(R.id.common_dialog_loading_progressbar);
    }

    public LoadingDialog(Context context, String loadingMsg) {
        this(context);
        setContent(loadingMsg);
    }

    public LoadingDialog(Context context, int loadingMsgId) {
        this(context);
        setContent(loadingMsgId);
    }

    /**
     * 设置弹出框的背景
     */
    public void setLoadingDialogBackGround(int resourceId) {
        if (null != dialogView) {
            dialogView.setBackgroundResource(resourceId);
        }
    }

    /**
     * 设置弹出框的背景
     */
    public void setLoadingDialogBackGround(Drawable drawable) {
        if (null != dialogView) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                dialogView.setBackgroundDrawable(drawable);
            } else {
                dialogView.setBackground(drawable);
            }
        }
    }

    /**
     * 获取弹出框view
     *
     * @return dialogView
     */
    public View getLoadingDialogView() {
        return dialogView;
    }

    /**
     * 获取弹出框的textview
     *
     * @return textView
     */
    public TextView getTextView() {
        return textView;
    }

    /**
     * 设置弹出框的文字内容
     */
    public void setContent(String content) {
        if (null != textView) {
            textView.setText(content);
        }
    }

    /**
     * 设置弹出框的文字内容
     */
    public void setContent(int resourceId) {
        if (null != textView) {
            textView.setText(resourceId);
        }
    }

    /**
     * 显示弹出框
     */
    public void show() {
        dialog.show();
    }

    /**
     * 隐藏弹出框
     */
    public void hide() {
        dialog.hide();
    }

    /**
     * 取消弹出框
     */
    public void dismiss() {
        dialog.dismiss();
    }

    public boolean isShowing() {
        return dialog.isShowing();
    }

    public void setCancelable(boolean cancelable) {
        dialog.setCancelable(cancelable);
    }

    public static class LoadingDialogDelegate {

        private LoadingDialog mLoadingDialog;

        public LoadingDialogDelegate(Context context) {
            mLoadingDialog = new LoadingDialog(context);
        }

        public LoadingDialogDelegate(Context context, String loadingMsg) {
            mLoadingDialog = new LoadingDialog(context, loadingMsg);
        }

        public LoadingDialogDelegate(Context context, int loadingMsgId) {
            mLoadingDialog = new LoadingDialog(context, loadingMsgId);
        }

        public void showLoadingDialog(int msg) {
            if (mLoadingDialog != null && !mLoadingDialog.isShowing()) {
                mLoadingDialog.setContent(msg);
                mLoadingDialog.show();
            }
        }

        public void showLoadingDialog() {
            if (mLoadingDialog != null && !mLoadingDialog.isShowing()) {
                mLoadingDialog.show();
            }
        }

        public void showLoadingDialog(String msg) {
            if (mLoadingDialog != null && !mLoadingDialog.isShowing()) {
                mLoadingDialog.setContent(msg);
                mLoadingDialog.show();
            }
        }

        public void setContent(String content) {
            mLoadingDialog.setContent(content);
        }

        public boolean isShowingDialog() {
            return mLoadingDialog == null ? false : mLoadingDialog.isShowing();
        }

        public void dismissLoadingDialog() {
            if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
                mLoadingDialog.dismiss();
            }
        }
    }
}
