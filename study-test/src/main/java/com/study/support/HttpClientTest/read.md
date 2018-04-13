#HttpClient连接测试:
##1、添加依赖
    <dependency>
           <groupId>commons-httpclient</groupId>
           <artifactId>commons-httpclient</artifactId>
           <version>3.1</version>
     </dependency>

##2、连接代码
HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod("http://www.apache.org");
        try {
            client.executeMethod(method);
            byte[] responseBody = method.getResponseBody();
            String returnData = new String(responseBody,"utf-8");
            System.out.println(returnData);
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            method.releaseConnection();
        }

##3、分析HttpClient代码