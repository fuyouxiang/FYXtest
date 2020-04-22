package java.test.bishi;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;



/**
 * @author 程婧
 */
public class Test {
    /**
     * 调用对方接口方法
     * @param path 第三方url
     * @param data 发送的数据，json
     */
    public static void interfaceUtil(String path,String data) {
        try {
            URL url = new URL(path);
            //打开和url之间的连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            PrintWriter out = null;
            //只传keyword=三阳开泰，不需要json，注掉请求方式
            //conn.setRequestMethod("POST");
            //设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)"); 
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            //发送请求参数
            out.print(data);
            //缓冲数据
            out.flush();
            
            // 定义BufferedReader输入流来读取URL的响应
            BufferedReader in = null;
            String result = "";
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            //解析json对象
            JSONObject jsStr = JSONObject.fromObject(result);
            String doc = jsStr.get("doc");
            System.out.println("json中的doc内容是："+doc);
            
            //方法一：正则表达式
            String regex="<content>(.*?)</content>";
            Pattern p=Pattern.compile(regex);
            Matcher m=p.matcher(doc);
            while(m.find()){
            	System.out.println("content标签中间的内容解析为："+m.group(1));
            }

            //方法二：字符串截取
            String content = doc.substring(doc.indexOf("<content>"), doc.indexOf("</content>")).substring("<content>".length());
            System.out.println("content标签中间的内容解析为："+content);
            
            //关闭连接
            in.close();
            conn.disconnect();
            System.out.println("调用结束");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        interfaceUtil("http://wap.123.com.inner/web/json4xmldoc.jsp?keyword=三阳开泰", "");
    }
}