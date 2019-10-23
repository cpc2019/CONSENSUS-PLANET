package com.pingan.baselibs;

public class SettingsDataProvider {

    /**
     * 语言id
     */
    public interface LanguageId {

        /**
         * 简体中文
         */
        int SIMPLIFIED_CHINESE = 0;

        /**
         * 繁体中文
         */
        int TRADITIONAL_CHINESE = 1;

        /**
         * English
         */
        int ENGLISH = 2;

    }

    /**
     * 计价方式id
     */
    public interface CaculateWayId {

        /**
         * 人民币
         */
        int RMB = 0;

        /**
         * 美元
         */
        int DOLLAR = 1;

    }

    /**
     * 根据语言id获取语言名称
     *
     * @param languageId
     *
     * @return
     */
    public static String getLanguageNameById(int languageId) {
        switch (languageId) {
            case LanguageId.SIMPLIFIED_CHINESE:
                return ApplicationManager.getContext().getString(R.string.simplified_chinese);
            case LanguageId.TRADITIONAL_CHINESE:
                return ApplicationManager.getContext().getString(R.string.traditional_chinese);
            case LanguageId.ENGLISH:
                return ApplicationManager.getContext().getString(R.string.english);
        }
        return null;
    }

    /**
     * 根据计价方式id获取语言名称
     *
     * @param caculateWayId
     *
     * @return
     */
    public static String getCaculateWayNameById(int caculateWayId) {
        switch (caculateWayId) {
            case CaculateWayId.RMB:
                return ApplicationManager.getContext().getString(R.string.rmb);
            case CaculateWayId.DOLLAR:
                return ApplicationManager.getContext().getString(R.string.dollar);
        }
        return null;
    }



}
