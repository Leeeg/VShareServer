package com.lee.vshare.common.util;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

/**
 * @Create by lee
 * @emil JefferyLeeeg@gmail.com
 * @Date 18-12-20
 * @Time 下午8:59
 */
public class MarkDownUtil {

    public static String markDown(String content) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(content.replace("<br>","\n"));
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }

//    public static void main(String[] args) {

//        String md1 = "noteContent\": \"##### Windows启、停MySql ``` win+R 键入：  net start mysql net stop mysql  注：windows不能直接restart Mysql;    如果是mysql 8.0，需要在命令后写mysql80 ``` ___  ##### XML5个转义符 ``` <    &lt; >    &gt; &    &amp; ”    &quot; ©    &apos;  注：\\\"&\\\"和\\\";\\\"是成对的 ``` ---";
//
//        String md = "##### XML5个转义符\n```\n" +
//                "<    &lt;\n" +
//                ">    &gt;\n" +
//                "&    &amp;\n" +
//                "”    &quot;\n" +
//                "©    &apos;\n" +
//                "\n" +
//                "注：\"&\"和\";\"是成对的\n" +
//                "```\n" +
//                "---";
//
//        String md3 = "##### Windows启、停MySql<br>```<br>win+R 键入：<br><br>net start mysql<br>net stop mysql<br><br>注：windows不能直接restart Mysql;    如果是mysql 8.0，需要在命令后写mysql80<br>```<br>___<br><br>##### XML5个转义符<br>```<br><    &lt;<br>>    &gt;<br>&    &amp;<br>”    &quot;<br>©    &apos;<br><br>注：\"&\"和\";\"是成对的<br>```<br>---";
//
//        String md4 = "##### Windows启、停MySql<br>```<br>win+R 键入：<br><br>net start mysql<br>net stop mysql<br><br>注：windows不能直接restart Mysql;    如果是mysql 8.0，需要在命令后写mysql80<br>```<br>___<br><br>##### XML5个转义符<br>```<br><    &lt;<br>>    &gt;<br>&    &amp;<br>”    &quot;<br>©    &apos;<br><br>注：\"&\"和\";\"是成对的<br>```<br>---";
//
//        String md5 = "##### Windows启、停MySql<br>```<br>win+R 键入：<br><br>net start mysql<br>net stop mysql<br><br>注：windows不能直接restart Mysql;    如果是mysql 8.0，需要在命令后写mysql80<br>```<br>___<br><br>##### XML5个转义符<br>```<br><    &lt;<br>>    &gt;<br>&    &amp;<br>”    &quot;<br>©    &apos;<br><br>注：\\\"&\\\"和\\\";\\\"是成对的<br>```<br>---";
//        System.out.println(MarkDownUtil.markDown(md5.replace("<br>","\n")));

//        double rec = 1945279/1024;
//        double sen = 1112883/1024;
//
//        double sun = 200/5/0.8;
//
//        double max = 500*1024/sun/60;
//
//        System.err.println(rec + "k");
//        System.err.println(sen + "k");
//
//        System.err.println(sun + "k");
//        System.err.println(max + "h");
//
//
//    }

}
