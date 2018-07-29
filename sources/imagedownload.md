### 图片下载原理


1. 使用OKHttpClient或HttpConnection 发送一个网络请求，返回一个inputStream 转化为byte数组，
通过BitmapFactory 将byte数组转化成bitmap，此时可以直接使用，也可以存储到本地


        /**
         * Get data from stream
         * @param inStream
         * @return byte[]
         * @throws Exception
         */
        public static byte[] readStream(InputStream inStream) throws Exception{
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while( (len=inStream.read(buffer)) != -1){
                outStream.write(buffer, 0, len);
            }
            outStream.close();
            inStream.close();
            return outStream.toByteArray();
        }
        
        
        
  // 使用OKHttp就可以通过response.body获取
  
  
      
      private void asyncGet() {
            client = new OkHttpClient();
            final Request request = new Request.Builder().get()
                    .url(IMAGE_URL)
                    .build();
    
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
    
                }
    
                @Override
                public void onResponse(Call call, Response response) throws IOException {
    
                    Message message = handler.obtainMessage();
                    if (response.isSuccessful()) {
                        message.what = IS_SUCCESS;
                        message.obj = response.body().bytes();
                        handler.sendMessage(message);
                    } else {
                        handler.sendEmptyMessage(IS_FAIL);
                    }
                }
            });
        }