package ai.zhuanzhi.plugins;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.plugin.net.OkHttpRequester;
import okhttp3.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * WebCollector使用阿布云代理的Http请求插件
 * <p>
 * 插件使用方法：
 * crawler.setRequester(new crawler.requester.plugin.AbuyunDynamicProxyRequester("阿布云动态代理用户名", "阿布云动态代理密码"));
 * 一行代码解决，无需自定义代理池及其它代理切换规则
 * <p>
 * 阿布云代理官网：
 * https://www.abuyun.com/
 */
public class AbuyunDynamicProxyRequester extends OkHttpRequester {

    String credential;

    public AbuyunDynamicProxyRequester(String proxyUser, String proxyPass) {
        credential = Credentials.basic(proxyUser, proxyPass);
        removeSuccessCode(301);
        removeSuccessCode(302);
    }

    @Override
    public OkHttpClient.Builder createOkHttpClientBuilder() {
        String proxyHost = "proxy.abuyun.com";
        int proxyPort = 9020;

        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));

        return super.createOkHttpClientBuilder()
                .proxy(proxy)
                .proxyAuthenticator(new Authenticator() {
//                    @Override
                    public Request authenticate(Route route, Response response) throws IOException {
                        return response.request().newBuilder()
                                .header("Proxy-Authorization", credential)
                                .build();
                    }
                });
    }


    public static void main(String[] args) throws Exception {
        final String username = "H960K1987AJB64HD";
        final String password = "3D64768712FE8C7D";
        AbuyunDynamicProxyRequester requester = new AbuyunDynamicProxyRequester(username, password);
        System.out.println(requester.getResponse(new CrawlDatum("https://36kr.com")).html());
//        System.out.println(requester.getResponse(new CrawlDatum("https://ruby-china.org/topics?page=2")).getHtmlByCharsetDetect());

    }
}
