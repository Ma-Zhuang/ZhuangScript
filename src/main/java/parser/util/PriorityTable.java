package parser.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 二元表达式优先级表
 */
public class PriorityTable {
    private List<List<String>> table = new ArrayList<>();

    public PriorityTable(){
        /**
         * 第一级优先级
         */
        table.add(Arrays.asList(new String[]{
                "&",
                "|",
                "^"
        }));
        /**
         * 第二级优先级
         */
        table.add(Arrays.asList(new String[]{
                "==",
                "!=",
                ">",
                "<",
                ">=",
                "<="
        }));
        /**
         * 第三级优先级
         */
        table.add(Arrays.asList(new String[]{
                "+",
                "-"
        }));
        /**
         * 第四级优先级
         */
        table.add(Arrays.asList(new String[]{
                "*",
                "/"
        }));
        /**
         * 第五级优先级
         */
        table.add(Arrays.asList(new String[]{
                "<<",
                ">>"
        }));
    }

    /**
     * 优先级的数量
     * @return
     */
    public int size(){
        return table.size();
    }

    /**
     * 获取优先级表
     * @param level
     * @return
     */
    public List<String> get(int level){
        return table.get(level);
    }
}
