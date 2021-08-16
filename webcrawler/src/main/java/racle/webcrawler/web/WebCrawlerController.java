package racle.webcrawler.web;

import org.springframework.web.bind.annotation.RestController;

import racle.webcrawler.service.RacleWebcrawler;

import java.io.Writer;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class WebCrawlerController {

	@RequestMapping("/")
	public String index() {
		return "Please pass parameter SearchURL & SearchText。For example, http://localhost:8080/webcrawler?SearchURL=https://www.dbs.com.sg/personal/default.page&SearchText=Card";
	}
	
	@RequestMapping("/webcrawler")
	public void webCrawler(@RequestParam("SearchURL") String searchURL, @RequestParam("SearchText") String searchText, Writer outputWritter) {
		RacleWebcrawler racleWebcrawler = new RacleWebcrawler(outputWritter);
		racleWebcrawler.getPageLinks(searchURL, 0, searchText);		
	}

}
