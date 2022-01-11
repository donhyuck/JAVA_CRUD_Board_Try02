package board.service;

import java.util.List;

import board.container.Container;
import board.dto.Article;
import board.util.Util;

public class ExportService {

	ArticleService articleService;
	MemberService memberService;

	public ExportService() {
		articleService = Container.articleService;
		memberService = Container.memberService;
	}

	public void makeHtml() {
		List<Article> articles = articleService.getForPrintArticles();

		for (Article article : articles) {
			String writerName = memberService.getMemberNameById(article.memberId);

			String fileName = article.id + ".html";
			String html = "<meta charset=\"UTF-8\">";
			html += "<div> 번 호 : " + article.id + "</div>";
			html += "<div> 제 목 : " + article.title + "</div>";
			html += "<div> 내 용 : " + article.body + "</div>";
			html += "<div> 작성일 : " + article.regDate + "</div>";
			html += "<div> 작성자 : " + writerName + "</div>";
			html += "<div> 조회수 : " + article.hit + "</div>";

			if (article.id > 1) {
				html += "<div><a href=\"" + (article.id - 1) + ".html\">이전글</a></div>";
			}

			html += "<div><a href=\"" + (article.id + 1) + ".html\">다음글</a></div>";

			Util.writeFileContents("exportHtml/" + fileName, html);
		}
	}

}