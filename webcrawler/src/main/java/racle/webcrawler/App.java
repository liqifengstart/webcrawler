package racle.webcrawler;

import racle.webcrawler.service.RacleWebcrawler;


public class App 
{
    public static void main( String[] args )
    {
		String text = "Card";
		//String URL = "https://www.dbs.com.sg/";
		String URL = "https://www.dbs.com.sg/personal/default.page";
		//String URL = "https://en.wikipedia.org/wiki/Cricket_World_Cup";

		new RacleWebcrawler().getPageLinks(URL, 0, text);
    }
}
