package board;

import java.util.Scanner;

public class BoardMain {
	public static void main(String[] args) {
		System.out.println("== ���α׷� ���� ==");
		Scanner sc = new Scanner(System.in);

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

			} else if (command.equals("article list")) {
				System.out.println("== �Խñ� ��� ==");

			} else if (command.equals("article write")) {
				System.out.println("== �Խñ� �ۼ� ==");

				int id = 1;

				System.out.print("�� ���� : ");
				String title = sc.nextLine();

				System.out.print("�� ���� : ");
				String body = sc.nextLine();

				System.out.printf("%d�� ���� �ۼ��Ǿ����ϴ�.\n", id);

			} else {
				System.out.printf("%s�� �������� �ʴ� ��ɾ��Դϴ�.\n", command);
				continue;
			}

		}
	}
}
