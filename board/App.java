package board;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import board.controller.ArticleController;
import board.controller.MemberController;
import board.dto.Article;
import board.dto.Member;
import board.util.Util;

public class App {

	private static List<Article> articles;
	private static List<Member> members;

	App() {
		articles = new ArrayList<>();
		members = new ArrayList<>();
	}

	public void start() {
		System.out.println("== ���α׷� ���� ==");

		makeTestDate();

		Scanner sc = new Scanner(System.in);

		// ArticleController ����
		ArticleController articleController = new ArticleController(sc, articles);
		MemberController memberController = new MemberController(sc, members);

		while (true) {
			System.out.print("��ɾ� : ");
			String command = sc.nextLine();

			command.trim();

			if (command.length() == 0) {
				continue;
			}

			if (command.equals("system exit")) {
				System.out.println("== ���α׷� ���� ==");
				break;

			} else if (command.equals("member join")) {

				memberController.doJoin();

			} else if (command.startsWith("article list")) {

				articleController.showList(command);

			} else if (command.equals("article write")) {

				articleController.doWrite();

			} else if (command.startsWith("article modify ")) {

				articleController.doModify(command);

			} else if (command.startsWith("article delete ")) {

				articleController.doDelete(command);

			} else if (command.startsWith("article detail ")) {

				articleController.showDetail(command);

			} else {
				System.out.printf("%s�� �������� �ʴ� ��ɾ��Դϴ�.\n", command);
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
