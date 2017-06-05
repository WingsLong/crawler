package crawler.zhilian;

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
    private static final String website = "zhilian";

    @Override
    public void process(Page page) {
        Selectable selectable = page.getHtml().css("div.newlist_list_content").links().regex("http://company.zhaopin.com/.*.htm");
        for(Selectable s : selectable.nodes()){
            LinkService.insert(s.get(),website);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        String url = "http://sou.zhaopin.com/jobs/searchresult.ashx?jl=广州&kw=java&sm=0&sg=0908602324284164974c1e507b54d5c7&p=";
        int pageSize = 62;
        for(int i=1;i<=pageSize;i++){
            Spider.create(new JobList()).addUrl(url+i).thread(1).run();
            System.out.println("&curr_page="+i);
        }
    }
}
