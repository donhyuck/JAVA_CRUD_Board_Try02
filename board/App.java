package board;

import java.util.Scanner;

import board.controller.ArticleController;
import board.controller.Controller;
import board.controller.MemberController;

public class App {

	public void start() {
		System.out.println("== ���α׷� ���� ==");

		Scanner sc = new Scanner(System.in);

		ArticleController articleController = new ArticleController(sc);
		MemberController memberController = new MemberController(sc);

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
			}

			String[] commandBits = command.split(" ");

			if (commandBits.length == 1) {
				System.out.println("�������� �ʴ� ��ɾ��Դϴ�.");
				continue;
			}

			String controllerName = commandBits[0];
			String actionMethodName = commandBits[1];

			Controller controller = null;

			if (controllerName.equals("article")) {
				controller = articleController;

			} else if (controllerName.equals("member")) {
				controller = memberController;

			} else {
				System.out.printf("%s�� �������� �ʴ� ��ɾ��Դϴ�.\n", command);
				continue;
			}

			controller.doAction(command, actionMethodName);

		}

		sc.close();
	}

}
