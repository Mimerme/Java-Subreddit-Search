package io.github.mimerme.Java_Subreddit_Search;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class App 
{
	static String thread;
	static String[] subreddits;
	
	static ArrayList<String> users = new ArrayList<String>();
	static ArrayList<String> matches = new ArrayList<String>();
	
    public static void main( String[] args ) throws IOException, InterruptedException
    {
    	Scanner in = new Scanner(System.in);
    	System.out.println("Enter thread URL");
    	thread = in.next();
    	System.out.println("Enter subreddits to search for (sperated by commas no spaces)");
    	String temp = in.next();
    	subreddits = temp.split(",");
    	
		System.out.print("Loading the following subreddits: ");
    	for (String string : subreddits) {
    		System.out.print(string + ", ");
		}
    	System.out.println();
    	
    	System.out.println("--------------CRAWLING STARTED-------------");
    	Document doc = Jsoup.connect(thread).userAgent("<Java_Desktop>:<io.github.mimerme.Reddit-Subreddit-Search>:<0.5> (by /u/Mimerme)").get();
    	Elements authors = doc.getElementsByClass("author");
    	    	
    	for (Element element : authors) {
    		if(!users.contains(element.attr("href"))){
    			users.add(element.attr("href"));
    			System.out.println("Loading user " + element.attr("href"));
    			Elements t_Subreddits = Jsoup.connect(element.attr("href"))
    					.userAgent("<Java_Desktop>:<io.github.mimerme.Reddit-Subreddit-Search>:<0.5> (by /u/Mimerme)")
    					.get().getElementsByClass("subreddit");
    			for (Element subreddit : t_Subreddits) {
					if(Arrays.asList(subreddits).contains(subreddit.text()) ||
							Arrays.asList(subreddits).contains(subreddit.text().toLowerCase()) ||
							Arrays.asList(subreddits).contains(subreddit.text().toUpperCase())){
						System.out.println("A match has been found for user " + element.attr("href"));
						matches.add(element.attr("href"));
					}
				}
    		}
		}
    	System.out.println("--------------CRAWLING FINISHED-------------");
    	
    	System.out.println("=======================================");
    	System.out.println("=======================================");
    	System.out.println("=======================================");
    	System.out.println("=======================================");
    	System.out.println("=======================================");
    	System.out.println("=======================================");
    	System.out.println("=======================================");
    	System.out.println("=======================================");
    	System.out.println("=======================================");

    	System.out.println("===================MATCH LIST====================");
    	for (String s : matches) {
    		System.out.println(s);
		}
    	System.out.println("Open all matches in browser? (Y/N) Case Sensitive");
    	if(in.next().equals("Y")){
    		for (String match : matches) {
				openWebpage(new URL(match));
			}
    	}
    }
    
    public static void openWebpage(URL url) {
        try {
            openWebpage(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
    
    public static void openWebpage(URI uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
