package crawler.job_51;

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
    private static final String website = "51job";

    @Override
    public void process(Page page) {
        Selectable selectable = page.getHtml().links().regex("http://jobs.51job.com/all/.*");
        for(Selectable s : selectable.nodes()){
            LinkService.insert(s.get(),website);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        int pageSize = 187;
       String url = "http://search.51job.com/jobsearch/search_result.php?fromJs=1&jobarea=030200%2C00&district=000000&funtype=0000&industrytype=00&issuedate=9&providesalary=99&keyword=java&keywordtype=2&lang=c&stype=1&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&lonlat=0%2C0&radius=-1&ord_field=0&list_type=0&fromType=14&dibiaoid=0&confirmdate=9&curr_page=";
/*        String url = "http://search.51job.com/jobsearch/search_result.php?fromJs=1&jobarea=030600%2C00&district=000000&funtype=0000&industrytype=00&issuedate=9&providesalary=99&keyword=java&keywordtype=2&lang=c&stype=1&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&lonlat=0%2C0&radius=-1&ord_field=0&list_type=0&fromType=14&dibiaoid=0&confirmdate=9&curr_page=";
        int pageSize = 12;*/
        for(int i=1;i<=pageSize;i++){
            Spider.create(new JobList()).addUrl(url+i).thread(1).run();
            System.out.println("&curr_page="+i);
        }
    }
}
