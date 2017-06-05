package crawler.lagou;

import service.LinkService;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

/**
 * Created by tan on 2017/1/23.
 */
public class JobList implements PageProcessor  {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
    private static final String website = "lagou";

    @Override
    public void process(Page page) {
        Selectable selectable = page.getHtml().links().regex("https://www.lagou.com/gongsi/.*.html");
        for(Selectable s : selectable.nodes()){
            LinkService.insert(s.get(),website);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        String url = "https://www.lagou.com/zhaopin/Java/";
        int pageSize = 30;
        for(int i=1;i<=pageSize;i++){
            Spider.create(new JobList()).addUrl(url+i).thread(1).run();
            System.out.println("&curr_page="+i);
        }
    }
}
