package board;

import java.util.Scanner;

import board.controller.ArticleController;
import board.controller.Controller;
import board.controller.ExportController;
import board.controller.MemberController;

public class App {

	public void start() {
		System.out.println("== ���α׷� ���� ==");

		Scanner sc = new Scanner(System.in);

		ArticleController articleController = new ArticleController(sc);
		MemberController memberController = new MemberController(sc);
		ExportController exportController = new ExportController(sc);

		articleController.makeTestData();
		memberController.makeTestData();
		exportController.makeTestData();

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

			} else if (controllerName.equals("export")) {
				controller = exportController;

			} else {
				System.out.printf("%s�� �������� �ʴ� ��ɾ��Դϴ�.\n", command);
				continue;
			}

			// �α���, �α׾ƿ� üũ�� ��ɾ �����鼭 �����Ѵ�.
			String actionName = controllerName + "/" + actionMethodName;

			switch (actionName) {
			case "article/write":
			case "article/delete":
			case "article/modify":
			case "member/logout":
				if (Controller.isLogined() == false) {
					System.out.println("�α��� �� �̿����ּ���.");
					continue;
				}
				break;
			}

			switch (actionName) {
			case "member/join":
			case "member/login":
				if (Controller.isLogined() == true) {
					System.out.println("�α׾ƿ� �� �̿����ּ���.");
					continue;
				}
				break;
			}

			controller.doAction(command, actionMethodName);

		}

		sc.close();
	}

}
