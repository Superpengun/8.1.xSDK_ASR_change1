package com.sinovoice.example;

import com.sinovoice.hcicloudsdk.common.utils.HttpPostUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Getnlu {
    private final static String cloudurl="";
    //private void


    }
//public void postRequest() {
//    new Thread(new Runnable() {
//        @Override
//        public void run() {
//            OutputStream outputStream = null;
//            InputStream inputStream = null;
//            try {
//                URL url = new URL(BASE_URL + "/post/comment");
//                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                httpURLConnection.setRequestMethod("POST");
//                httpURLConnection.setConnectTimeout(10000);
//                httpURLConnection.setRequestProperty("Content-Type","application/json;charset=UTF-8");
//                httpURLConnection.setRequestProperty("Accept-Language","zh-CN,zh;q=0.9");
//                httpURLConnection.setRequestProperty("Accept","application/json, text/plain, */*");
//                CommentItem commentItem = new CommentItem("234134123","你的文章写得也太好了！");
//                Gson gson = new Gson();
//                String jsonStr = gson.toJson(commentItem);
//                byte[] bytes = jsonStr.getBytes("UTF-8");
//                Log.d(TAG,"jsonStr length -- > " + bytes.length);
//                httpURLConnection.setRequestProperty("Content-Length",String.valueOf(bytes.length));
//                //连接
//                httpURLConnection.connect();
//                //把数据给到服务
//                outputStream = httpURLConnection.getOutputStream();
//                outputStream.write(bytes);
//                outputStream.flush();
//                //拿结果
//                int responseCode = httpURLConnection.getResponseCode();
//                if(responseCode == HttpURLConnection.HTTP_OK) {
//                    inputStream = httpURLConnection.getInputStream();
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//                    Log.d(TAG,"result -- > " + bufferedReader.readLine());
//                }
//            } catch(Exception e) {
//                e.printStackTrace();
//            } finally {
//                IOUtils.ioClose(inputStream);
//                IOUtils.ioClose(outputStream);
//            }
//        }
//    }).start();
//}
