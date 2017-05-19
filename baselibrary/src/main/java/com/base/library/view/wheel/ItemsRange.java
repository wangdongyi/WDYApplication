package com.base.library.view.wheel;

/**
 * @author 王东一
 * @ClassName: ItemsRange
 * @Description:
 * @date 2015-1-9 下午3:09:30
 */
public class ItemsRange {
    // 在当前位
    private int first;

    // Items数量
    private int count;

    /**
     * 初始化
     */
    public ItemsRange() {
        this(0, 0);
    }

    /**
     * <p>
     * Title:
     * </p>
     * <p>
     * Description:
     * </p>
     *
     * @param first
     * @param count
     */
    public ItemsRange(int first, int count) {
        this.first = first;
        this.count = count;
    }

    /**
     * 得到最前面的值
     */
    public int getFirst() {
        return first;
    }

    /**
     * 得到最后一个值
     */
    public int getLast() {
        return getFirst() + getCount() - 1;
    }

    /**
     * 得到item的数量
     */
    public int getCount() {
        return count;
    }

    /**
     * 是一位还是最后一位
     */
    public boolean contains(int index) {
        return index >= getFirst() && index <= getLast();
    }
}
