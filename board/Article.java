package board;

public class Article {
	int id;
	String regDate;
	String title;
	String body;
	int hit;

	public Article(int id, String regDate, String title, String body) {
		this.id = id;
		this.regDate = regDate;
		this.title = title;
		this.body = body;
		this.hit = 0;
	}

	public void increaseHit() {
		hit++;
	}

}
