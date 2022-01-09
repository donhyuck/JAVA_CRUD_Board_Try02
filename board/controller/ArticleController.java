package board.controller;

import java.util.List;
import java.util.Scanner;

import board.container.Container;
import board.dto.Article;
import board.dto.Member;
import board.service.ArticleService;
import board.util.Util;

public class ArticleController extends Controller {

	private Scanner sc;
	private String command;
	private String actionMethodName;
	private ArticleService articleService;

	public ArticleController(Scanner sc) {
		this.sc = sc;
		articleService = Container.articleService;
	}

	public void doAction(String command, String actionMethodName) {
		this.command = command;
		this.actionMethodName = actionMethodName;

		switch (actionMethodName) {
		case "list":
			showList();
			break;
		case "write":
			doWrite();
			break;
		case "modify":
			doModify();
			break;
		case "delete":
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

		String searchKeyword = command.substring("article list".length()).trim();

		List<Article> forPrintArticles = articleService.getForPrintArticles(searchKeyword);

		if (forPrintArticles.size() == 0) {
			System.out.println("�˻������ �������� �ʽ��ϴ�.");
			return;
		}

		System.out.println("== �Խñ� ��� ==");
		System.out.println("��ȣ /   �ۼ���   / �ۼ��� / ���� / ��ȸ��");

		for (int i = forPrintArticles.size() - 1; i >= 0; i--) {

			Article currentArticle = forPrintArticles.get(i);

			String writerName = null;

			List<Member> members = Container.memberDao.members;

			for (Member member : members) {
				if (currentArticle.memberId == member.id) {
					writerName = member.name;
					break;
				}
			}

			System.out.printf(" %d / %s / %s / %s / %d\n", currentArticle.id, currentArticle.regDate, writerName,
					currentArticle.title, currentArticle.hit);
		}

	}

	private void doWrite() {

		System.out.println("== �Խñ� �ۼ� ==");

		int id = articleService.getNewId();

		System.out.print("�� ���� : ");
		String title = sc.nextLine();

		System.out.print("�� ���� : ");
		String body = sc.nextLine();

		String regDate = Util.getCurrentDate();

		Article article = new Article(id, regDate, loginedMember.id, title, body);

		articleService.add(article);

		System.out.printf("%d�� ���� ��ϵǾ����ϴ�.\n", id);

	}

	private void doModify() {

		String[] commandBits = command.split(" ");
		int id = Integer.parseInt(commandBits[2]);

		Article foundArticle = articleService.getArticleById(id);

		if (foundArticle == null) {
			System.out.printf("%d�� �Խñ��� ã�� �� �����ϴ�.\n", id);
			return;
		}

		if (foundArticle.memberId != loginedMember.id) {
			System.out.println("�ش� �Խñۿ� ���� ������ �����ϴ�.");
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

		Article foundArticle = articleService.getArticleById(id);

		if (foundArticle == null) {
			System.out.printf("%d�� �Խñ��� ã�� �� �����ϴ�.\n", id);
			return;
		}

		if (foundArticle.memberId != loginedMember.id) {
			System.out.println("�ش� �Խñۿ� ���� ������ �����ϴ�.");
			return;
		}

		articleService.remove(foundArticle);

		System.out.printf("%d�� �Խñ��� �����Ǿ����ϴ�.\n", id);

	}

	private void showDetail() {

		String[] commandBits = command.split(" ");
		int id = Integer.parseInt(commandBits[2]);

		Article foundArticle = articleService.getArticleById(id);

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

	public void makeTestData() {
		System.out.println("�׽�Ʈ�� ���� �Խñ� �����͸� �����մϴ�.");

		articleService
				.add(new Article(Container.articleDao.getNewId(), Util.getCurrentDate(), 1, "test1", "test1", 11));
		articleService
				.add(new Article(Container.articleDao.getNewId(), Util.getCurrentDate(), 2, "test2", "test2", 21));
		articleService
				.add(new Article(Container.articleDao.getNewId(), Util.getCurrentDate(), 3, "test3", "test2", 31));
	}
}
