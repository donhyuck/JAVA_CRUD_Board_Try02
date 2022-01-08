package board.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import board.dto.Article;
import board.util.Util;

public class ArticleController extends Controller {

	private Scanner sc;
	private List<Article> articles;
	private String command;
	private String actionMethodName;

	public ArticleController(Scanner sc) {
		this.sc = sc;
		articles = new ArrayList<>();
	}

	public void doAction(String command, String actionMethodName) {
		this.command = command;
		this.actionMethodName = actionMethodName;

		switch (actionMethodName) {
		case "list":
			showList();
			break;
		case "write":
			if (isLogined() == false) {
				System.out.println("�α��� �� �̿����ּ���.");
				return;
			}
			doWrite();
			break;
		case "modify":
			if (isLogined() == false) {
				System.out.println("�α��� �� �̿����ּ���.");
				return;
			}
			doModify();
			break;
		case "delete":
			if (isLogined() == false) {
				System.out.println("�α��� �� �̿����ּ���.");
				return;
			}
			doDelete();
			break;
		case "detail":
			showDetail();
			break;
		default:
			System.out.println("�������� �ʴ� ��ɾ��Դϴ�.");
			break;
		}
	}

	private void showList() {

		if (articles.size() == 0) {
			System.out.println("��ϵ� �Խñ��� �����ϴ�.");
			return;
		}

		String searchKeyword = command.substring("article list".length()).trim();

		List<Article> forListArticles = articles;

		if (searchKeyword.length() > 0) {
			forListArticles = new ArrayList<>();

			for (Article article : articles) {
				if (article.title.contains(searchKeyword)) {
					forListArticles.add(article);
				}
			}

			if (forListArticles.size() == 0) {
				System.out.println("�˻������ �������� �ʽ��ϴ�.");
				return;
			}

		}

		System.out.println("== �Խñ� ��� ==");
		System.out.println("��ȣ /   �ۼ���   / �ۼ��� / ���� / ��ȸ��");

		for (int i = forListArticles.size() - 1; i >= 0; i--) {

			Article currentArticle = forListArticles.get(i);
			System.out.printf(" %d / %s / %d / %s / %d\n", currentArticle.id, currentArticle.regDate,
					currentArticle.memberId, currentArticle.title, currentArticle.hit);
		}

	}

	private void doWrite() {

		System.out.println("== �Խñ� �ۼ� ==");

		int id = articles.size() + 1;

		System.out.print("�� ���� : ");
		String title = sc.nextLine();

		System.out.print("�� ���� : ");
		String body = sc.nextLine();

		String regDate = Util.getCurrentDate();

		Article article = new Article(id, regDate, loginedMember.id, title, body);

		articles.add(article);

		System.out.printf("%d�� ���� ��ϵǾ����ϴ�.\n", id);

	}

	private void doModify() {

		String[] commandBits = command.split(" ");
		int id = Integer.parseInt(commandBits[2]);

		Article foundArticle = getArticleById(id);

		if (foundArticle == null) {
			System.out.printf("%d�� �Խñ��� ã�� �� �����ϴ�.\n", id);
			return;
		}

		System.out.println("== �Խñ� ���� ==");

		System.out.print("�� ���� : ");
		String title = sc.nextLine();

		System.out.print("�� ���� : ");
		String body = sc.nextLine();

		foundArticle.title = title;
		foundArticle.body = body;

		System.out.printf("%d�� �Խñ��� �����Ǿ����ϴ�.\n", id);

	}

	private void doDelete() {

		String[] commandBits = command.split(" ");
		int id = Integer.parseInt(commandBits[2]);

		int foundIndex = getArticleIndexById(id);

		if (foundIndex == -1) {
			System.out.printf("%d�� �Խñ��� ã�� �� �����ϴ�.\n", id);
			return;
		}

		articles.remove(foundIndex);

		System.out.printf("%d�� �Խñ��� �����Ǿ����ϴ�.\n", id);

	}

	private void showDetail() {

		String[] commandBits = command.split(" ");
		int id = Integer.parseInt(commandBits[2]);

		Article foundArticle = getArticleById(id);

		if (foundArticle == null) {
			System.out.printf("%d�� �Խñ��� ã�� �� �����ϴ�.\n", id);
			return;
		}

		foundArticle.increaseHit();

		System.out.println("== �Խñ� �󼼺��� ==");

		System.out.printf("�� ȣ : %d\n", foundArticle.id);
		System.out.printf("�ۼ��� : %s\n", foundArticle.regDate);
		System.out.printf("�ۼ��� : %d\n", foundArticle.memberId);
		System.out.printf("�� �� : %s\n", foundArticle.title);
		System.out.printf("�� �� : %s\n", foundArticle.body);
		System.out.printf("��ȸ�� : %d\n", foundArticle.hit);

	}

	private int getArticleIndexById(int id) {

		int i = 0;

		for (Article article : articles) {
			if (article.id == id) {
				return i;
			}

			i++;
		}

		return -1;
	}

	private Article getArticleById(int id) {

		int index = getArticleIndexById(id);

		if (index != -1) {
			return articles.get(index);
		}

		return null;
	}

	public void makeTestData() {
		System.out.println("�׽�Ʈ�� ���� �Խñ� �����͸� �����մϴ�.");
		articles.add(new Article(1, Util.getCurrentDate(), 1, "test1", "test1", 11));
		articles.add(new Article(2, Util.getCurrentDate(), 2, "test2", "test2", 21));
		articles.add(new Article(3, Util.getCurrentDate(), 3, "test3", "test2", 31));
	}
}
