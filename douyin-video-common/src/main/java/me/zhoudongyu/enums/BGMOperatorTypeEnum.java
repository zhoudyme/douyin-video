package me.zhoudongyu.enums;


/**
 * 背景音乐操作
 *
 * @author Steve
 * @date 2019/05/04
 */
public enum BGMOperatorTypeEnum {

    //添加bgm
    ADD("1", "添加bgm"),
    //删除bgm
    DELETE("2", "删除bgm");

    public final String type;
    public final String value;

    BGMOperatorTypeEnum(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public String getUserType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public static String getValueByKey(String key) {
        for (BGMOperatorTypeEnum type : BGMOperatorTypeEnum.values()) {
            if (type.getUserType().equals(key)) {
                return type.value;
            }
        }
        return null;
    }

}
