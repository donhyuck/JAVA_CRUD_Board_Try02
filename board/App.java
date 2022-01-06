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

		Scanner sc = new Scanner(System.in);

		ArticleController articleController = new ArticleController(sc, articles);
		MemberController memberController = new MemberController(sc, members);

		articleController.makeTestData();
		memberController.makeTestData();

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

}
