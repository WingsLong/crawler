package crawler.job_51;

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
    private static final String website = "51job";

    @Override
    public void process(Page page) {
        String company = page.getHtml().xpath("//div[@class='con_msg']/tidyText()").get();
        String jobs = page.getHtml().xpath("//div[@class='dw_table']/tidyText()").get();
        ContentService.insert(company,0,page.getUrl().get(),website,jobs);
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
