package board;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
				System.out.println("== ȸ�� ���� ==");

				String loginId = null;

				while (true) {
					System.out.print("�α��� ���̵� : ");
					loginId = sc.nextLine();

					// �α��� �ߺ��� ��������
					if (isJoinableLoginedId(loginId) == false) {
						System.out.printf("%s�� �̹� ������� ���̵��Դϴ�.\n", loginId);
						continue;
					}

					break;
				}

				// ������ ���̵� ����

				String loginPw = null;
				String loginPwConfirm = null;

				while (true) {
					System.out.print("�α��� ��й�ȣ : ");
					loginPw = sc.nextLine();

					System.out.print("�α��� ��й�ȣ Ȯ�� : ");
					loginPwConfirm = sc.nextLine();

					// ��й�ȣ Ȯ�ΰ� ��ġ���� �ʴ� ���
					if (loginPwConfirm.equals(loginPw) == false) {
						System.out.println("��й�ȣ�� �ٽ��Է����ּ���.");
						continue;
					}

					// ��й�ȣ ��ġ
					break;
				}

				System.out.print("�̸� : ");
				String name = sc.nextLine();

				// �α��� ���̵�� ������ ��й�ȣ�� ����
				int id = members.size() + 1;
				String regDate = Util.getCurrentDate();

				Member member = new Member(id, regDate, loginId, loginPw, name);
				members.add(member);

				System.out.printf("%s�� ȸ�������� �Ϸ�Ǿ����ϴ�.\n", name);

			} else if (command.startsWith("article list")) {

				if (articles.size() == 0) {
					System.out.println("��ϵ� �Խñ��� �����ϴ�.");
					continue;
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
						continue;
					}

				}

				System.out.println("== �Խñ� ��� ==");
				System.out.println("��ȣ /   �ۼ���   / ���� / ��ȸ��");

				for (int i = forListArticles.size() - 1; i >= 0; i--) {

					Article currentArticle = forListArticles.get(i);
					System.out.printf(" %d / %s / %s / %d\n", currentArticle.id, currentArticle.regDate,
							currentArticle.title, currentArticle.hit);
				}

			} else if (command.equals("article write")) {
				System.out.println("== �Խñ� �ۼ� ==");

				int id = articles.size() + 1;

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

				Article foundArticle = getArticleById(id);

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

				int foundIndex = getArticleIndexById(id);

				if (foundIndex == -1) {
					System.out.printf("%d�� �Խñ��� ã�� �� �����ϴ�.\n", id);
					continue;
				}

				articles.remove(foundIndex);

				System.out.printf("%d�� �Խñ��� �����Ǿ����ϴ�.\n", id);

			} else if (command.startsWith("article detail ")) {

				String[] commandBits = command.split(" ");
				int id = Integer.parseInt(commandBits[2]);

				Article foundArticle = getArticleById(id);

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
				System.out.printf("%s�� �������� �ʴ� ��ɾ��Դϴ�.\n", command);
				continue;
			}

		}
	}

	// loginId �� ��ġ�ϴ� ��ȣ�� ã�´�.
	// ��ġ�ϴ� �� ���ٸ� = -1

	private int getMemberIndexByLoginId(String loginId) {

		int i = 0;

		for (Member member : members) {

			if (member.loginId.equals(loginId)) {
				return i;
			}

			i++;
		}

		return -1;
	}

	// �ߺ��� ���̵� �����Ƿ� ȸ�������� �����ϴ�.
	private boolean isJoinableLoginedId(String loginId) {

		int index = getMemberIndexByLoginId(loginId);

		if (index == -1) {
			return true;
		}

		return false;
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

	private static void makeTestDate() {
		System.out.println("�׽�Ʈ�� ���� �Խñ� �����͸� �����մϴ�.");
		articles.add(new Article(1, Util.getCurrentDate(), "test1", "test1", 11));
		articles.add(new Article(2, Util.getCurrentDate(), "test2", "test2", 21));
		articles.add(new Article(3, Util.getCurrentDate(), "test3", "test2", 31));
	}

}
