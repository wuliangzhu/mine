package com.mye.mine.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 通过post方式调用http请求接口的工具类
 */
public class HttpUtil {
    private final static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    private static final String MYE_WEB_API_URL = "http://localhost:9090";
    private static final String MYE_ALGORITHM_API_URL = "http://localhost:9090";

    public static final String TOKEN = "8f841e87-24b0-a4f3-5017-d58d6bad5e04";

    /**
     * 调用mye_web_api接口
     *
     * @param httpURL
     * @param obj
     * @return
     */
    public static String sendPostHttpRequest(String httpURL, Map<String, Object> obj) {
        BufferedReader in = null;
        StringBuffer result = new StringBuffer();
        try {
            StringBuffer params = new StringBuffer();
            String API_URL = MYE_WEB_API_URL;
            httpURL = API_URL + "/" + httpURL;
            if (obj != null) {
                for (Map.Entry<String, Object> item : obj.entrySet()) {
                    if (item.getValue() != null) {
                        params.append(item.getKey() + "=" + URLEncoder.encode(item.getValue().toString(), "UTF-8") + "&");
                    }
                }
            }
            if (params.length() > 0) {
                params.deleteCharAt(params.length() - 1);
            }
            logger.info("------HttpRequestUrl URL=: " + httpURL + ",params=" + params.toString());
            URL url = new URL(httpURL);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("POST");// 提交模式
            // 设置发送数据的格式
            httpConn.setDoInput(true);
            httpConn.setUseCaches(false);
            httpConn.setDoOutput(true);
            httpConn.setConnectTimeout(20000);
            httpConn.setReadTimeout(300000);

            byte[] bypes = params.toString().getBytes();
            httpConn.getOutputStream().write(bypes);// 输入参数
            in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));// 注意，这里要声明字符编码是UTF-8，否则会乱码
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result.toString();
    }
    /**
     * 用于调用mye_weather_api接口
     *
     * @param httpURL
     * @param obj
     * @return
     */
    public static String sendPostHttpRequest_Weather(String httpURL, Map<String, Object> obj) {
        BufferedReader in = null;
        StringBuffer result = new StringBuffer();
        try {
            StringBuffer params = new StringBuffer();
            if (obj != null) {
                for (Map.Entry<String, Object> item : obj.entrySet()) {
                    if (item.getValue() != null) {
                        params.append(item.getKey() + "=" + URLEncoder.encode(item.getValue().toString(), "UTF-8") + "&");
                    }
                }
            }
            if (params.length() > 0) {
                params.deleteCharAt(params.length() - 1);
            }
            logger.info("------HttpRequestUrl URL=: " + httpURL + ",params=" + params.toString());
            URL url = new URL(httpURL);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("POST");// 提交模式
            // 设置发送数据的格式
            httpConn.setDoInput(true);
            httpConn.setUseCaches(false);
            httpConn.setDoOutput(true);
            httpConn.setConnectTimeout(20000);
            httpConn.setReadTimeout(300000);

            byte[] bypes = params.toString().getBytes();
            httpConn.getOutputStream().write(bypes);// 输入参数
            in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));// 注意，这里要声明字符编码是UTF-8，否则会乱码
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result.toString();
    }
    /**
     * 用于调用mye_algorithm_api接口
     *
     * @param httpURL
     * @param obj
     * @return
     */
    public static String sendPostHttpRequest_Algorithm(String httpURL, Map<String, Object> obj) {
        BufferedReader in = null;
        StringBuffer result = new StringBuffer();
        try {
            StringBuffer params = new StringBuffer();
            String API_URL = "";
            httpURL = API_URL + "/" + httpURL;
            if (obj != null) {
                for (Map.Entry<String, Object> item : obj.entrySet()) {
                    if (item.getValue() != null) {
                        params.append(item.getKey() + "=" + URLEncoder.encode(item.getValue().toString(), "UTF-8") + "&");
                    }
                }
            }
            if (params.length() > 0) {
                params.deleteCharAt(params.length() - 1);
            }
            logger.info("------HttpRequestUrl URL=: " + httpURL + ",params=" + params.toString());
            URL url = new URL(httpURL);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("POST");// 提交模式
            // 设置发送数据的格式
            httpConn.setDoInput(true);
            httpConn.setUseCaches(false);
            httpConn.setDoOutput(true);
            httpConn.setConnectTimeout(20000);
            httpConn.setReadTimeout(300000);

            byte[] bypes = params.toString().getBytes();
            httpConn.getOutputStream().write(bypes);// 输入参数
            in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));// 注意，这里要声明字符编码是UTF-8，否则会乱码
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result.toString();
    }

    /**
     * 用于调用mye_algorithm_api接口
     *
     * @param httpURL
     * @param obj
     * @return
     */
    public static String sendPostHttpRequest_Energy(String httpURL, Map<String, Object> obj) {
        BufferedReader in = null;
        StringBuffer result = new StringBuffer();
        try {
            StringBuffer params = new StringBuffer();
            String API_URL = "";
            httpURL = API_URL + "/" + httpURL;
            if (obj != null) {
                for (Map.Entry<String, Object> item : obj.entrySet()) {
                    if (item.getValue() != null) {
                        params.append(item.getKey() + "=" + URLEncoder.encode(item.getValue().toString(), "UTF-8") + "&");
                    }
                }
            }
            if (params.length() > 0) {
                params.deleteCharAt(params.length() - 1);
            }
            logger.info("------HttpRequestUrl URL=: " + httpURL + ",params=" + params.toString());
            URL url = new URL(httpURL);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("POST");// 提交模式
            // 设置发送数据的格式
            httpConn.setDoInput(true);
            httpConn.setUseCaches(false);
            httpConn.setDoOutput(true);
            httpConn.setConnectTimeout(20000);
            httpConn.setReadTimeout(300000);

            byte[] bypes = params.toString().getBytes();
            httpConn.getOutputStream().write(bypes);// 输入参数
            in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));// 注意，这里要声明字符编码是UTF-8，否则会乱码
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result.toString();
    }


    /**
     * 用于调用mye_analysis_api接口
     *
     * @param httpURL
     * @param obj
     * @return
     */
    public static String sendPostHttpRequest_Analysis(String httpURL, Map<String, Object> obj) {
        BufferedReader in = null;
        StringBuffer result = new StringBuffer();
        try {
            StringBuffer params = new StringBuffer();
            String API_URL = "";
            httpURL = API_URL + "/" + httpURL;
            if (obj != null) {
                for (Map.Entry<String, Object> item : obj.entrySet()) {
                    if (item.getValue() != null) {
                        params.append(item.getKey() + "=" + URLEncoder.encode(item.getValue().toString(), "UTF-8") + "&");
                    }
                }
            }
            if (params.length() > 0) {
                params.deleteCharAt(params.length() - 1);
            }
            logger.info("------HttpRequestUrl URL=: " + httpURL + ",params=" + params.toString());
            URL url = new URL(httpURL);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("POST");// 提交模式
            // 设置发送数据的格式
            httpConn.setDoInput(true);
            httpConn.setUseCaches(false);
            httpConn.setDoOutput(true);
            httpConn.setConnectTimeout(20000);
            httpConn.setReadTimeout(300000);

            byte[] bypes = params.toString().getBytes();
            httpConn.getOutputStream().write(bypes);// 输入参数
            in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));// 注意，这里要声明字符编码是UTF-8，否则会乱码
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result.toString();
    }

    public static String sendPost_Optimalroom(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 调用通用接口
     *
     * @param httpURL
     * @param obj
     * @return
     */
    public static String sendCommonPostHttpRequest(String httpURL, Map<String, Object> obj) {
        BufferedReader in = null;
        StringBuffer result = new StringBuffer();
        try {
            StringBuffer params = new StringBuffer();
            if (obj != null) {
                for (Map.Entry<String, Object> item : obj.entrySet()) {
                    if (item.getValue() != null) {
                        params.append(item.getKey() + "=" + URLEncoder.encode(item.getValue().toString(), "UTF-8") + "&");
                    }
                }
            }
            if (params.length() > 0) {
                params.deleteCharAt(params.length() - 1);
            }
            logger.info("------HttpRequestUrl URL=: " + httpURL + ",params=" + params.toString());
            URL url = new URL(httpURL);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("POST");// 提交模式
            // 设置发送数据的格式
            httpConn.setDoInput(true);
            httpConn.setUseCaches(false);
            httpConn.setDoOutput(true);
            httpConn.setConnectTimeout(20000);
            httpConn.setReadTimeout(300000);

            byte[] bypes = params.toString().getBytes();
            httpConn.getOutputStream().write(bypes);// 输入参数
            in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));// 注意，这里要声明字符编码是UTF-8，否则会乱码
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result.toString();
    }

    public static String sendPostHttpRequest_url(String url, Map<String, Object> obj) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            StringBuffer param = new StringBuffer();
            for (Map.Entry<String, Object> item : obj.entrySet()) {
                param.append(item.getKey() + "=" + item.getValue() + "&");
            }
            if (param.length() > 0) {
                param.deleteCharAt(param.length() - 1);
            }
            logger.info("------HttpRequestUrl params:{} ", url + "/" + param.toString());
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            logger.info("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        } finally { // 使用finally块来关闭输出流、输入流
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
    /**
     * 测试样例
     *
     * @param args
     */
    public static void main(String[] args) {
        Map<String, Object> obj = new HashMap<String, Object>();
        String url = "elec_findAcDeviceByTids.jhtml";
//        String url = Constants.URL_ADD_USER;
//        String token = PropertyUtil.getProperties("api_token");
        obj.put("token", TOKEN);
        obj.put("beginTime", "2019-05-17 12:00:00");
        obj.put("endTime", "2019-05-27 12:00:00");
        String reslut = sendPostHttpRequest(url, obj);
        System.out.println(reslut);
    }
}
