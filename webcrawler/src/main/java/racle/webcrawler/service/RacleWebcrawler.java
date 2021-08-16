package racle.webcrawler.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;

public class RacleWebcrawler {

	private static final int MAX_DEPTH = 6;
	private static final int MAX_RESULTS_SIZE = 200;

	private HashSet<String> links;
	private int resultNumber = 0;
	private Writer outputWriter;

	public RacleWebcrawler() {
		links = new HashSet<String>();
	}
	
	public RacleWebcrawler(Writer writer) {
		links = new HashSet<String>();
		outputWriter = writer;		
	}

	public void getPageLinks(String URL, int depth, String text) {
		 String logPrefix = ">> Search Text: " + text + " Start from [" + URL + "] depth: [" + depth +  "] resultNumber: ["+resultNumber+"]";
		 System.out.println(logPrefix);
		// String logPrefix = "Searching: Node URL: [" + URL + "] with search
		// text:[" + text + "]";
		// System.out.println(logPrefix + "start getPageLinks() ");
		
		if(URL==null || "".equalsIgnoreCase(URL)){
			System.err.println("Invalid request with empty URL");
			return;
		}
		
		if(depth >= MAX_DEPTH ){
			System.out.println("The depth exceeds MAX_DEPTH.Stop searching. ");
			return;
		}
		
		if(resultNumber >= MAX_RESULTS_SIZE){
			System.out.println("The resultNumber exceeds MAX_RESULTS_SIZE.Stop searching.");
			return;
		}		

		if (!links.contains(URL)) {
			try {
				links.add(URL);
				
				depth++;
				Document document = Jsoup.connect(URL).ignoreHttpErrors(true).get();

				Elements linksOnPage = null;
				if (text != null && !"".equalsIgnoreCase(text.trim())) {
					linksOnPage = document.select("a[href]:contains(" + text.trim() + ")");
				} else {
					linksOnPage = document.select("a[href]");
				}
								
				for (Element page : linksOnPage) {	
					resultNumber++;
					
					String resultOutput = "<br> Result found No." + resultNumber + " depth: [" + depth + "] link: ["
							+ page.attr("abs:href") + "] with text: [" + page.text() + "]";
					
					System.out.println(resultOutput);
					outputWriter.write(resultOutput);
					outputWriter.flush();
										
					getPageLinks(page.attr("abs:href"), depth, text);
				}
			} catch (IOException e) {
				System.err.println("For '" + URL + "': " + e.getMessage());
			}
		}

		// System.out.println(logPrefix + "end getPageLinks() ");
	}

	public void getPageLinks(String URL, int depth) {
		getPageLinks(URL, depth, "");
	}

	public static void main(String[] args) {
		String text = "Card";
		String URL = "https://www.dbs.com.sg/";
		// 1. Pick a URL from the frontier
		String logPrefix = "Searching: Node URL: [" + URL + "] with search text:[" + text + "]";
		System.out.println(logPrefix + "start getPageLinks() ");
		String parameter = ">> MAX_DEPTH: " + MAX_DEPTH + " MAX_RESULTS_SIZE: " + MAX_RESULTS_SIZE;
		System.out.println(parameter);

		// new RacleWebcrawler().getPageLinks(URL, 0);

		new RacleWebcrawler().getPageLinks(URL, 0, text);
		System.out.println(logPrefix + "end getPageLinks() ");
	}

}