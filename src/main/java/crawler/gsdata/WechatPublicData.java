package crawler.gsdata;

import crawler.job_51.JobContent;
import service.LinkService;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;
import us.codecraft.webmagic.utils.HttpConstant;
import util.UrlUtils;

/**
 * 微信公众号数据爬取
 *
 */
public class WechatPublicData implements PageProcessor  {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100)
            .setDomain("gsdata.cn")

            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.98 Safari/537.36");;

    private static String newArticleUrl = "http://www.gsdata.cn/rank/toparc?wxname=#WXNAME#&wx=txsports&sort=-1";



    @Override
    public void process(Page page) {
        String pageUrl = page.getUrl().get();
        System.out.println(pageUrl);
        //①、清博搜索列表页
        if(pageUrl.startsWith("http://www.gsdata.cn/query/wx?q=")) {
            Selectable select = page.getHtml().css("div#query_wx>ul.imgword-list>li.list_query>div.img-word>div.word a#nickname");
            //========= 找出所有的搜索结果公众号 =========
            for(Selectable node : select.nodes()) {
                page.addTargetRequest(node.xpath("//a/@href").get());
            }
        }
        //②、清博数据微信详情页
        else if(pageUrl.startsWith("http://www.gsdata.cn/rank/wxdetail?wxname=")) {
            //========= 获取gsdata中的wxname，作为微信查询的key
            String wxname = UrlUtils.getUrlParameter(pageUrl, "wxname");
            Request request = new Request(newArticleUrl.replace("#WXNAME#", wxname));
            request.setMethod(HttpConstant.Method.POST);
            page.addTargetRequest(request);
        }
        //③、清博数据温馨文章top10，含最新、阅读数、点赞数
        else if(pageUrl.startsWith("http://www.gsdata.cn/rank/toparc?wxname=")) {
            System.out.println("=================================");
            System.out.println(page.getJson());

        }



    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new WechatPublicData()).addUrl("http://www.gsdata.cn/query/wx?q=txsports").thread(1).run();
    }


}
