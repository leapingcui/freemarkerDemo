package com.cy.ftl;

import com.cy.common.Student;
import freemarker.template.Configuration;
import freemarker.template.Template;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.*;

/**
 * XML+Freemarker生成复杂word（包含图片）
 * 1、用office2003或者以上的版 本编辑好 word的样式
 * 2、然后另存为Word XML文档
 * 3、将后缀名“xml”修改为“ftl”，模板制作完成
 */
public class Main {

    public static void main(String[] args) {

        try {
            // 第一步：把freemarker的jar包添加到工程中
            // 第二步：freemarker的运行不依赖web容器，可以在java工程中运行。创建一个测试方法进行测试。
            // 第三步：创建一个Configuration对象
            Configuration configuration = new Configuration(Configuration.getVersion());
            // 第四步：告诉config对象模板文件存放的路径。
            configuration.setDirectoryForTemplateLoading(new File("D:/JavaDevelop/idea_workplace/freemarkerDemo/web/WEB-INF/ftl"));
            // 第五步：设置config的默认字符集。一般是utf-8
            configuration.setDefaultEncoding("utf-8");
            // 第六步：从config对象中获得模板对象。需要制定一个模板文件的名字。
            Template template = configuration.getTemplate("infoWithPhotos.ftl");
            // 第七步：创建模板需要的数据集。可以是一个map对象也可以是一个pojo，把模板需要的数据都放入数据集。
            List<Student> students = new ArrayList<>();
            students.add(new Student("001", "cuiyue", "男"));
            students.add(new Student("002", "cuiyue2", "男"));
            students.add(new Student("003", "cuiyue3", "男"));
            Map map = new HashMap<>();
            map.put("students", students);
            map.put("curDate", new Date());
            map.put("image", getImageStr());
            // 第八步：创建一个Writer对象，指定生成的文件保存的路径及文件名。
            Writer out = new FileWriter(new File("E:/resource/MyFiles/info.doc"));
            // 第九步：调用模板对象的process方法生成静态文件。需要两个参数数据集和writer对象。
            template.process(map, out);
            // 第十步：关闭writer对象。
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getImageStr() {
        String imgFile = "E:/entertainment/testPotos/jinyong.jpg";
        InputStream in = null;
        byte[] data = null;
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

}
