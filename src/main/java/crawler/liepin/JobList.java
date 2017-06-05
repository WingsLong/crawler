package crawler.liepin;

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
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100)
            .setDomain(".liepin.com")
            .addCookie("em_token","YWMtCmNAYuIREeaukK3y-bUAawAAAVsEoZQlhvU93NalR55peLWySCwjJuCT0FY");
    private static final String website = "liepin";

    @Override
    public void process(Page page) {
        Selectable selectable = page.getHtml().links().regex("https://www.liepin.com/a/.*.shtml");
        for(Selectable s : selectable.nodes()){
           // LinkService.insert(s.get(),website);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        String url = "https://www.liepin.com/zhaopin/?imscid=R000000058&dqs=050020&industries=040&salary=9%2418&key=java&curPage=";
        int pageSize = 1;
        for(int i=0;i<=pageSize;i++){
            Spider.create(new JobList()).addUrl(url+i).thread(1).run();
            System.out.println("&curr_page="+i);
        }
    }
}
