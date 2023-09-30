package com.llgululu.app.util;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;

public class Generator {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/llremove?serverTimezone=Asia/Shanghai", "root", "lh20020123")
                .globalConfig(builder -> {
                    builder.author("llgululu") // 设置作者
                            .outputDir("E:\\curriculumDesign\\comprehensive\\removeMask\\src\\main\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.llgululu.app"); // 设置父包名
                })
                .strategyConfig(builder -> {
                    builder.addInclude("total"); // 设置需要生成的表名
                })
                .execute();
    }
}
