package cn.fangcai.blog.uitls;

import cn.hutool.core.util.StrUtil;

import java.util.stream.Collectors;

/**
 * @author MouFangCai
 * @date 2024/10/30 17:08
 * @description
 */
public class FcStrUtil {
    public static String subStrByLinesLimit(String input, Byte limitRatio) {
        if (StrUtil.isBlank(input) || limitRatio == null) {
            return input;
        }
        // 使用 \n 拆分字符串为行，并转换为 List
        var lines = input.lines().toList();
        // 计算需要保留的行数，至少保留 1 行
        int retainCount = Math.max(1, (int) Math.round(lines.size() * limitRatio / 100.0));
        // 使用流保留指定数量的行并连接成字符串
        return lines.stream()
                .limit(retainCount)
                .collect(Collectors.joining("\n"));
    }

    public static void main(String[] args) {
        String inputText = """
                Line 1: This is the first line.
                Line 2: This is the second line.
                Line 3: This is the third line.
                Line 4: This is the fourth line.
                Line 5: This is the fifth line.
                Line 6: This is the fifth line.
                """;

        byte percentage = -10;
        String result = subStrByLinesLimit(inputText, percentage);
        System.out.println("Result:\n" + result);
    }
}
