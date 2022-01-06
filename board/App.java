package board;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import board.dto.Article;
import board.util.Util;

public class App {

	private static List<Article> articles;

	App() {
		articles = new ArrayList<>();
	}

	public void start() {
		System.out.println("== ���α׷� ���� ==");

		makeTestDate();

		Scanner sc = new Scanner(System.in);

		int lastId = 1;

		while (true) {
			System.out.print("���ɾ� : ");
			String command = sc.nextLine();

			command.trim();

			if (command.length() == 0) {
				continue;
			}

			if (command.equals("system exit")) {
				System.out.println("== ���α׷� ���� ==");
				break;

			} else if (command.equals("article list")) {

				if (articles.size() == 0) {
					System.out.println("��ϵ� �Խñ��� �����ϴ�.");
					continue;
				}

				System.out.println("== �Խñ� ��� ==");
				System.out.println("��ȣ /   �ۼ���   / ���� / ��ȸ��");
				for (int i = articles.size() - 1; i >= 0; i--) {
					Article currentArticle = articles.get(i);
					System.out.printf(" %d / %s / %s / %d\n", currentArticle.id, currentArticle.regDate,
							currentArticle.title, currentArticle.hit);
				}

			} else if (command.equals("article write")) {
				System.out.println("== �Խñ� �ۼ� ==");

				int id = lastId++;

				System.out.print("�� ���� : ");
				String title = sc.nextLine();

				System.out.print("�� ���� : ");
				String body = sc.nextLine();

				String regDate = Util.getCurrentDate();

				Article article = new Article(id, regDate, title, body);

				articles.add(article);

				System.out.printf("%d�� ���� ��ϵǾ����ϴ�.\n", id);

			} else if (command.startsWith("article modify ")) {

				String[] commandBits = command.split(" ");
				int id = Integer.parseInt(commandBits[2]);

				Article foundArticle = null;

				for (int i = 0; i < articles.size(); i++) {

					Article currentArticle = articles.get(i);
					if (currentArticle.id == id) {
						foundArticle = currentArticle;
					}
				}

				if (foundArticle == null) {
					System.out.printf("%d�� �Խñ��� ã�� �� �����ϴ�.\n", id);
					continue;
				}

				System.out.println("== �Խñ� ���� ==");

				System.out.print("�� ���� : ");
				String title = sc.nextLine();

				System.out.print("�� ���� : ");
				String body = sc.nextLine();

				foundArticle.title = title;
				foundArticle.body = body;

				System.out.printf("%d�� �Խñ��� �����Ǿ����ϴ�.\n", id);

			} else if (command.startsWith("article delete ")) {

				String[] commandBits = command.split(" ");
				int id = Integer.parseInt(commandBits[2]);

				Article foundArticle = null;

				for (int i = 0; i < articles.size(); i++) {

					Article currentArticle = articles.get(i);
					if (currentArticle.id == id) {
						foundArticle = currentArticle;
					}
				}

				if (foundArticle == null) {
					System.out.printf("%d�� �Խñ��� ã�� �� �����ϴ�.\n", id);
					continue;
				}

				articles.remove(foundArticle);

				System.out.printf("%d�� �Խñ��� �����Ǿ����ϴ�.\n", id);

			} else if (command.startsWith("article detail ")) {

				String[] commandBits = command.split(" ");
				int id = Integer.parseInt(commandBits[2]);

				Article foundArticle = null;

				for (int i = 0; i < articles.size(); i++) {

					Article currentArticle = articles.get(i);
					if (currentArticle.id == id) {
						foundArticle = currentArticle;
					}
				}

				if (foundArticle == null) {
					System.out.printf("%d�� �Խñ��� ã�� �� �����ϴ�.\n", id);
					continue;
				}

				foundArticle.increaseHit();

				System.out.println("== �Խñ� �󼼺��� ==");

				System.out.printf("�� ȣ : %d\n", foundArticle.id);
				System.out.printf("�ۼ��� : %s\n", foundArticle.regDate);
				System.out.printf("�� �� : %s\n", foundArticle.title);
				System.out.printf("�� �� : %s\n", foundArticle.body);
				System.out.printf("��ȸ�� : %d\n", foundArticle.hit);

			} else {
				System.out.printf("%s�� �������� �ʴ� ���ɾ��Դϴ�.\n", command);
				continue;
			}

		}
	}

	private static void makeTestDate() {
		System.out.println("�׽�Ʈ�� ���� �Խñ� �����͸� �����մϴ�.");
		articles.add(new Article(1, Util.getCurrentDate(), "test1", "test1", 11));
		articles.add(new Article(2, Util.getCurrentDate(), "test2", "test2", 21));
		articles.add(new Article(3, Util.getCurrentDate(), "test3", "test2", 31));
	}

}