package crawler.lagou;

import pojo.Link;
import service.ContentService;
import service.LinkService;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * Created by tan on 2017/1/24.
 */
public class JobContent implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
    private static final String website = "lagou";

    @Override
    public void process(Page page) {
        String company = page.getHtml().xpath("//div[@class='item_content']/tidyText()").get();
        ContentService.insert(company,0,page.getUrl().get(),website,null);
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        List<Link> linkList = LinkService.getAll(website);
        for(Link link : linkList){
            Spider.create(new JobContent()).addUrl(link.getUrl()).thread(1).run();
            System.out.println("id="+link.getId());
        }
    }
}
