package com.pingan.browser;

public class BrowserManager {
	public static String token;
	public String local;
	public String time;

	private static BrowserManager instance;

	private BrowserManager() {
	}

	public static BrowserManager getInstance() {
		if (instance == null) {
			instance = new BrowserManager();
		}
		return instance;
	}

	public void init(String token, String time, String local) {
		this.token = token;
		this.local = local;
		this.time = time;
	}
}
