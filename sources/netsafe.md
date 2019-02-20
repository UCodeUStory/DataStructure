### 加密方案发展

1. 一开始使用非对称加密可以达到安全，但是效率太低，

2. 使用对称秘钥效率高，但如果写入客户端，被破解后就可以解密双方数据，因为服务端也是相同的秘钥

3. 服务端生成会话密钥，进行非对称加密下发，咋一看这个会话密钥是加密后在网络上传输的，但实际上里面存在一个问题：由于公钥是公开的，因此任何人都能用公钥解开并获取你的会话密钥。因此，这个方案也不够完善。
我们再回顾一下这个方案，发现存在一个本质的问题：这个会话密钥是服务端单独生成的，那一定会导致一种结果：只要客户端能最终解密出来，那黑客也一定能解密出来。因为对于服务端而言，客户端和黑客都是密钥的接收方，两者是平等的，无法区别的。

4. 客户端主动生成密钥

    具体方式如下：

    服务端明文下发公钥给客户端；
    客户端生成一个随机数作为会话密钥，利用公钥加密后发送给服务端；
    服务端收到后，通过私钥对密文进行解密并获取随机数。
    可以看出这个过程里，黑客就算拦截了我们的加密数据，也会因为没有私钥而无法获取会话密钥。

    看起来，方案很完美了，但实际上里面仍然存在一个漏洞：如果最开始下发的明文公钥就是假的呢？这会导致的结果是：用户全程和一个中间黑客通信：信任黑客的公钥，然后自以为安全的进行数据通信，结果把所有个人信息都暴露给黑客了，最重要的是，服务端对此一无所知！

5.  公钥的合法性校验
   为了公钥的合法性校验，人们建立了专门颁发证书的机构："证书中心"（Certificate authority，简称CA），它会利用自己的私钥，将我们的公钥和相关信息进行加密，生成一个数字证书。在客户端内部，会存在一个受信任的根证书颁发机构，所以客户端在每次拿到一个公钥后，就去查询这个公钥是否在这里受信任证书列表中，如果不在，则认为公钥无效，不再进行后续操作。如果存在，再继续进行下一步认证。

   而这也和Https的实现机制相近。




### https 原理

前提 非常重要的事情—加密方案中的加密算法都是公开的，而密钥是保密的,没有密钥就不能解密，有了密钥就能解密 *

三个角色       客户端              代理服务器                  真实服务器

所谓拦截破解拦截，就是代理服务器可以看到客户端发给服务器的数据和服务端返回给客户端的数据，并可以篡改


指定信任证书 https 验证流程，（还有不指定的，就是会默认用根证书验证，但不能保证唯一性）

用我们的证书去申请 CA证书，一对CA公钥 和 CA私钥

CA公钥留在客户端                    CA私钥留在服务端

建立链接握手过程中   -》 通过代理服务器   -》到达服务端

服务端返回-》代理服务器一个CA证书（用CA私钥加密过的，只能CA公钥去解，里面包含了我们自己的公钥），代理服务通过破解的App方式可以拿到证书（CA公钥去解密证书，拿到里面的公钥）通过公钥加密 自己生成对称秘钥反给服务器，服务器拿手里私钥去解密，可以达到通信（但这并无意，我们自己也不知道和服务器通信做什么）这里可以达到冒充客户端作用，应该没意义吧

代理服务器将CA证书给客户端，客户端通过使用本地CA证书公钥解密，验证通过，取出公钥，生成对称秘钥，然后加密返回代理服务器，代理服务器拿到之后没有私钥，不能解密取出对称秘钥，只能返回给服务器




#### 对于浏览器

客户不留你们的证书，只有几个最大的CA跟证书

通过CA根证书验证你们的CA证书，再通过CA证书验证网站证书

通过根证书验证 报CertificateException 验证失败，就会弹出是否要信任此证书，同意可继续浏览



#### 对于手机

内置也有几个最大的CA跟证书

当我们App使用https时，通常会报CertificateException，表示该证书用手机根证书验证失败，因此我们解决办法可能是信任所有证书，

在客户端中覆盖google默认的证书检查机制（X509TrustManager），并且在代码中无任何校验SSL证书有效性相关代码

           public class MySSLSocketFactory extends SSLSocketFactory {
        	    SSLContext sslContext = SSLContext.getInstance("TLS");
        	 
        	    public MySSLSocketFactory(KeyStore truststore)
        	        throws NoSuchAlgorithmException, KeyManagementException,
        	            KeyStoreException, UnrecoverableKeyException {
        	        super(truststore);
        	 
        	        TrustManager tm = new X509TrustManager() {
        	                public void checkClientTrusted(X509Certificate[] chain,
        	                    String authType) throws CertificateException {
       	                }
        	 
        	                 //客户端并未对SSL证书的有效性进行校验，并且使用了自定义方法的方式覆盖android自带的校验方法
        	                public void checkServerTrusted(X509Certificate[] chain,
        	                    String authType) throws CertificateException {
        	                }
        	 
        	                public X509Certificate[] getAcceptedIssuers() {
        	                    return null;
        	                }
        	            };
        	 
        	        sslContext.init(null, new TrustManager[] { tm }, null);
        	    }
        	}

#### 但问题出来了：

如果用户手机中安装了一个恶意证书，那么就可以通过中间人攻击的方式进行窃听用户通信以及修改request或者response中的数据。
手机银行中间人攻击过程：

1. 客户端在启动时，传输数据之前需要客户端与服务端之间进行一次握手，在握手过程中将确立双方加密传输数据的密码信息。
2. 中间人在此过程中将客户端请求服务器的握手信息拦截后，模拟客户端请求给服务器（将自己支持的一套加密规则发送给服务器），服务器会从中选出一组加密算法与HASH算法，并将自己的身份信息以证书的形式发回给客户端。证书里面包含了网站地址，加密公钥，以及证书的颁发机构等信息。
3. 而此时中间人会拦截下服务端返回给客户端的证书信息，并替换成自己的证书信息。
4. 客户端得到中间人的response后，会选择以中间人的证书进行加密数据传输。
5. 中间人在得到客户端的请求数据后，以自己的证书进行解密。
6. 在经过窃听或者是修改请求数据后，再模拟客户端加密请求数据传给服务端。就此完成整个中间人攻击的过程。



#### 防护办法：
   1.
       服务端使用CA机构颁发的证书，客户端默认用根证书验证就可以，（但只能验证是否是合法的CA证书，只要是CA证书都能通过验证，需要做访问域名限制，（还是说默认CA颁发的就不敢做坏事，做坏事可以查））

   2. 添加指定添加指定信任证书（这个证书可以是CA申请的(使用CA机构颁发证书的方式可行，但是如果与实际情况相结合来看的话，时间和成本太高，所以目前很少有用此办法来做。)，也可以是自己自签名生成的） 
       在客户端内部，会存在一个受信任的根证书颁发机构，所以客户端在每次拿到一个公钥后，就去查询这个公钥是否在这里受信任证书列表中，如果不在，则认为公钥无效，不再进行后续操作。如果存在，再继续进行下一步认证。



            public void initSSL() throws CertificateException, IOException, KeyStoreException,
                        NoSuchAlgorithmException, KeyManagementException {
                    CertificateFactory cf = CertificateFactory.getInstance("X.509");
                    InputStream in = getAssets().open("ca.crt");
                    Certificate ca = cf.generateCertificate(in);

                    KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
                    keystore.load(null, null);
                    keystore.setCertificateEntry("ca", ca);

                    String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
                    TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
                    tmf.init(keystore);

                    // Create an SSLContext that uses our TrustManager
                    SSLContext context = SSLContext.getInstance("TLS");
                    context.init(null, tmf.getTrustManagers(), null);
                    URL url = new URL("https://certs.cac.washington.edu/CAtest/");
            //        URL url = new URL("https://github.com");
                    HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                    urlConnection.setSSLSocketFactory(context.getSocketFactory());
                    InputStream input = urlConnection.getInputStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
                    StringBuffer result = new StringBuffer();
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    Log.e("TTTT", result.toString());
                }


- 由于手机银行服务器其实是固定的，所以证书也是固定的，可以使用“证书或公钥锁定”的办法来防护证书有效性未作验证的问题。这相当不用默认根证书去校验，而是自己去校验



        public final class PubKeyManager implements X509TrustManager {
        	    private static String PUB_KEY = "30820122300d06092a864886f70d0101" +
        	        "0105000382010f003082010a0282010100b35ea8adaf4cb6db86068a836f3c85"+
        	        "5a545b1f0cc8afb19e38213bac4d55c3f2f19df6dee82ead67f70a990131b6bc"+
        	        "ac1a9116acc883862f00593199df19ce027c8eaaae8e3121f7f329219464e657"+
        	        "2cbf66e8e229eac2992dd795c4f23df0fe72b6ceef457eba0b9029619e0395b8"+
        	        "609851849dd6214589a2ceba4f7a7dcceb7ab2a6b60c27c69317bd7ab2135f50"+
        	        "c6317e5dbfb9d1e55936e4109b7b911450c746fe0d5d07165b6b23ada7700b00"+
        	        "33238c858ad179a82459c4718019c111b4ef7be53e5972e06ca68a112406da38"+
        	        "cf60d2f4fda4d1cd52f1da9fd6104d91a34455cd7b328b02525320a35253147b"+
        	        "e0b7a5bc860966dc84f10d723ce7eed5430203010001";
        	 
        	     //锁定证书公钥在apk中
        	    public void checkServerTrusted(X509Certificate[] chain, String authType)
        	        throws CertificateException {
        	        if (chain == null) {
        	            throw new IllegalArgumentException(
                        "checkServerTrusted: X509Certificate array is null");
        	        }
        	 
        	        if (!(chain.length > 0)) {
        	            throw new IllegalArgumentException(
        	                "checkServerTrusted: X509Certificate is empty");
        	        }
        	 
        	        if (!((null != authType) && authType.equalsIgnoreCase("RSA"))) {
        	            throw new CertificateException(
        	                "checkServerTrusted: AuthType is not RSA");
        	        }
        	 
        	        // Perform customary SSL/TLS checks
        	        try {
        	            TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");
        	            tmf.init((KeyStore) null);
        	 
        	            for (TrustManager trustManager : tmf.getTrustManagers()) {
        	                ((X509TrustManager) trustManager).checkServerTrusted(chain,
        	                    authType);
        	            }
        	        } catch (Exception e) {
        	            throw new CertificateException(e);
        	        }
        	 
        	        // Hack ahead: BigInteger and toString(). We know a DER encoded Public Key begins
        	        // with 0?30 (ASN.1 SEQUENCE and CONSTRUCTED), so there is no leading 0?00 to drop.
        	        RSAPublicKey pubkey = (RSAPublicKey) chain[0].getPublicKey();
        	        String encoded = new BigInteger(1 /* positive */, pubkey.getEncoded()).toString(16);
        	 
        	        // Pin it!
        	        final boolean expected = PUB_KEY.equalsIgnoreCase(encoded);
        	 
        	        if (!expected) {
        	            throw new CertificateException(
        	                "checkServerTrusted: Expected public key: " + PUB_KEY +
        	                ", got public key:" + encoded);
        	        }
        	    }
        	}



  3. 上面方法都可避免中间攻击人冒充服务端，因为中间攻击人不能串改服务端证书，只能用和服务端相同的证书，即使证书我们可以通过服务端下发拿到，但是没有私钥也解密不了信息

  4. 以上如果以上还是存在一个问题，就是中间攻击人可以冒充客户端，使用根证书公钥进行解密获得公钥等信息，验证服务器数据签名，然后加密自己对称秘钥发给服务器，达到伪装客户端目的

  5. 为了客户端不被冒充使用Https双向验证，在客户端也保留个证书，发给服务端验证，但是如果客户端被破解取得证书也会被冒充



          public class SSLHelper {
              private static final String KEY_STORE_TYPE_BKS = "bks";
              private static final String KEY_STORE_TYPE_P12 = "PKCS12";
              public static final String KEY_STORE_CLIENT_PATH = "server.pfx";//P12文件
              private static final String KEY_STORE_TRUST_PATH = "client.truststore";//truststore文件
              public static final String KEY_STORE_PASSWORD = "123456";//P12文件密码
              private static final String KEY_STORE_TRUST_PASSWORD = "123456";//truststore文件密码

              public static SSLSocketFactory getSSLSocketFactory(Context context) {
                  SSLSocketFactory factory = null;

                  try {
                      // 服务器端需要验证的客户端证书
                      KeyStore keyStore = KeyStore.getInstance(KEY_STORE_TYPE_P12);
                      // 客户端信任的服务器端证书
                      KeyStore trustStore = KeyStore.getInstance(KEY_STORE_TYPE_BKS);

                      InputStream ksIn = context.getResources().getAssets()
                        .open(KEY_STORE_CLIENT_PATH);
                      InputStream tsIn = context.getResources().getAssets()
                        .open(KEY_STORE_TRUST_PATH);
                      try {
                          keyStore.load(ksIn, KEY_STORE_PASSWORD.toCharArray());
                          trustStore.load(tsIn, KEY_STORE_TRUST_PASSWORD.toCharArray());
                      } catch (Exception e) {
                          e.printStackTrace();
                      } finally {
                          try {
                              ksIn.close();
                          } catch (Exception e) {
                              e.printStackTrace();
                          }
                          try {
                              tsIn.close();
                          } catch (Exception e) {
                              e.printStackTrace();
                          }
                      }
                      //信任管理器
                      TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                        TrustManagerFactory.getDefaultAlgorithm());
                      trustManagerFactory.init(trustStore);
                      //密钥管理器
                      KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("X509");
                      keyManagerFactory.init(keyStore, KEY_STORE_PASSWORD.toCharArray());
                      //初始化SSLContext
                      SSLContext sslContext = SSLContext.getInstance("TLS");
                      sslContext.init(keyManagerFactory.getKeyManagers(),
                                      trustManagerFactory.getTrustManagers(), null);
                      factory =  sslContext.getSocketFactory();
                  } catch (NoSuchAlgorithmException e) {
                      e.printStackTrace();
                  } catch (KeyManagementException e) {
                      e.printStackTrace();
                  } catch (KeyStoreException e) {
                      e.printStackTrace();
                  } catch (IOException e) {
                      e.printStackTrace();
                  } catch (UnrecoverableKeyException e) {
                      e.printStackTrace();
                  }

                  return factory;
              }

          }

          OkHttpClient配置
          OkHttpClient client = new OkHttpClient.Builder()
            .sslSocketFactory(SSLHelper.getSSLSocketFactory(getApplication()))



  总结：Https双向验证是最终方案，可以防止服务端被冒充，也减少了客户端被冒充的几率，客户端即使被冒充也影响不大，因为一般我们都会在服务端做个总校验，

  一般 浏览器客户端被冒充，我们应该仔细观察域名


  - [关于Https安全性问题、双向验证防止中间人攻击问题](https://blog.csdn.net/u010731949/article/details/50538280)
  - [Android Https双向验证](https://www.jianshu.com/p/eef28062a2ea)
  - [Https单向认证和双向认证](https://blog.csdn.net/duanbokan/article/details/50847612)
