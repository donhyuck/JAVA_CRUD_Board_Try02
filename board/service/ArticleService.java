package board.service;

import java.util.List;

import board.container.Container;
import board.dao.ArticleDao;
import board.dto.Article;

public class ArticleService {

	private ArticleDao articleDao;

	public ArticleService() {
		articleDao = Container.articleDao;
	}

	public List<Article> getForPrintArticles(String searchKeyword) {
		return articleDao.getArticles(searchKeyword);
	}

	public Article getArticleById(int id) {
		return articleDao.getArticleById(id);
	}

	public void remove(Article foundArticle) {
		articleDao.remove(foundArticle);
	}

	public int getNewId() {
		return articleDao.getNewId();
	}

	public void add(Article article) {
		articleDao.add(article);
	}

}
