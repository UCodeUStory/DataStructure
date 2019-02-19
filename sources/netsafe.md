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


此过程相当于之拦截了一部分，服务端和客户端的通信能容还是不知道


所谓破解是破解其他人的内容，如果自己登录账号破解了也无用



手机里面也自带CA根证书，可以验证我们签发的CA

只要你们域名通过申请CA机构申请，颁发给你们证书，就能保证验证过程了，客户端只需要限制访问的域名就行

还有做双向认证的，就是客户端也集成一个客户端的证书，服务器做反向认证，防止非法设备访问网站


#### 对于浏览器

客户不留你们的证书，只有几个最大的CA跟证书

通过CA根证书验证你们的CA证书，再通过CA证书验证网站证书

通过根证书验证 报CertificateException 验证失败，就会弹出是否要信任此证书，同意可继续浏览



#### 对于手机

也有几个最大的CA跟证书

当我们App开发https时，通常会报CertificateException，表示该证书用手机根证书验证失败，因此我们解决办法可能是信任所有证书，

在客户端中覆盖google默认的证书检查机制（X509TrustManager），并且在代码中无任何校验SSL证书有效性相关代码

        01  public class MySSLSocketFactory extends SSLSocketFactory {
        02	    SSLContext sslContext = SSLContext.getInstance("TLS");
        03	 
        04	    public MySSLSocketFactory(KeyStore truststore)
        05	        throws NoSuchAlgorithmException, KeyManagementException,
        06	            KeyStoreException, UnrecoverableKeyException {
        07	        super(truststore);
        08	 
        09	        TrustManager tm = new X509TrustManager() {
        10	                public void checkClientTrusted(X509Certificate[] chain,
        11	                    String authType) throws CertificateException {
        12	                }
        13	 
        14	                 //客户端并未对SSL证书的有效性进行校验，并且使用了自定义方法的方式覆盖android自带的校验方法
        15	                public void checkServerTrusted(X509Certificate[] chain,
        16	                    String authType) throws CertificateException {
        17	                }
        18	 
        19	                public X509Certificate[] getAcceptedIssuers() {
        20	                    return null;
        21	                }
        22	            };
        23	 
        24	        sslContext.init(null, new TrustManager[] { tm }, null);
        25	    }
        26	}

但问题出来了：

如果用户手机中安装了一个恶意证书，那么就可以通过中间人攻击的方式进行窃听用户通信以及修改request或者response中的数据。
手机银行中间人攻击过程：
1 客户端在启动时，传输数据之前需要客户端与服务端之间进行一次握手，在握手过程中将确立双方加密传输数据的密码信息。
2 中间人在此过程中将客户端请求服务器的握手信息拦截后，模拟客户端请求给服务器（将自己支持的一套加密规则发送给服务器），服务器会从中选出一组加密算法与HASH算法，并将自己的身份信息以证书的形式发回给客户端。证书里面包含了网站地址，加密公钥，以及证书的颁发机构等信息。
3 而此时中间人会拦截下服务端返回给客户端的证书信息，并替换成自己的证书信息。
4 客户端得到中间人的response后，会选择以中间人的证书进行加密数据传输。
5 中间人在得到客户端的请求数据后，以自己的证书进行解密。
6 在经过窃听或者是修改请求数据后，再模拟客户端加密请求数据传给服务端。就此完成整个中间人攻击的过程。



#### 防护办法：
  1.
       使用CA机构颁发的证书默认用根证书验证就可以，（但只能验证是否是合法的CA证书，只要是CA证书都能通过验证，需要做访问域名限制，（还是说默认CA颁发的就不敢做坏事，做坏事可以查））

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


由于手机银行服务器其实是固定的，所以证书也是固定的，可以使用“证书或公钥锁定”的办法来防护证书有效性未作验证的问题。

这相当不用默认根证书去校验，而是自己去校验



        public final class PubKeyManager implements X509TrustManager {
        02	    private static String PUB_KEY = "30820122300d06092a864886f70d0101" +
        03	        "0105000382010f003082010a0282010100b35ea8adaf4cb6db86068a836f3c85"+
        04	        "5a545b1f0cc8afb19e38213bac4d55c3f2f19df6dee82ead67f70a990131b6bc"+
        05	        "ac1a9116acc883862f00593199df19ce027c8eaaae8e3121f7f329219464e657"+
        06	        "2cbf66e8e229eac2992dd795c4f23df0fe72b6ceef457eba0b9029619e0395b8"+
        07	        "609851849dd6214589a2ceba4f7a7dcceb7ab2a6b60c27c69317bd7ab2135f50"+
        08	        "c6317e5dbfb9d1e55936e4109b7b911450c746fe0d5d07165b6b23ada7700b00"+
        09	        "33238c858ad179a82459c4718019c111b4ef7be53e5972e06ca68a112406da38"+
        10	        "cf60d2f4fda4d1cd52f1da9fd6104d91a34455cd7b328b02525320a35253147b"+
        11	        "e0b7a5bc860966dc84f10d723ce7eed5430203010001";
        12	 
        13	     //锁定证书公钥在apk中
        14	    public void checkServerTrusted(X509Certificate[] chain, String authType)
        15	        throws CertificateException {
        16	        if (chain == null) {
        17	            throw new IllegalArgumentException(
        18	                "checkServerTrusted: X509Certificate array is null");
        19	        }
        20	 
        21	        if (!(chain.length > 0)) {
        22	            throw new IllegalArgumentException(
        23	                "checkServerTrusted: X509Certificate is empty");
        24	        }
        25	 
        26	        if (!((null != authType) && authType.equalsIgnoreCase("RSA"))) {
        27	            throw new CertificateException(
        28	                "checkServerTrusted: AuthType is not RSA");
        29	        }
        30	 
        31	        // Perform customary SSL/TLS checks
        32	        try {
        33	            TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");
        34	            tmf.init((KeyStore) null);
        35	 
        36	            for (TrustManager trustManager : tmf.getTrustManagers()) {
        37	                ((X509TrustManager) trustManager).checkServerTrusted(chain,
        38	                    authType);
        39	            }
        40	        } catch (Exception e) {
        41	            throw new CertificateException(e);
        42	        }
        43	 
        44	        // Hack ahead: BigInteger and toString(). We know a DER encoded Public Key begins
        45	        // with 0?30 (ASN.1 SEQUENCE and CONSTRUCTED), so there is no leading 0?00 to drop.
        46	        RSAPublicKey pubkey = (RSAPublicKey) chain[0].getPublicKey();
        47	        String encoded = new BigInteger(1 /* positive */, pubkey.getEncoded()).toString(16);
        48	 
        49	        // Pin it!
        50	        final boolean expected = PUB_KEY.equalsIgnoreCase(encoded);
        51	 
        52	        if (!expected) {
        53	            throw new CertificateException(
        54	                "checkServerTrusted: Expected public key: " + PUB_KEY +
        55	                ", got public key:" + encoded);
        56	        }
        57	    }
        58	}



