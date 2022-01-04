package board;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BoardMain {
	public static void main(String[] args) {
		System.out.println("== 프로그램 시작 ==");
		Scanner sc = new Scanner(System.in);

		List<Article> articles = new ArrayList<>();

		int lastId = 1;

		while (true) {
			System.out.print("명령어 : ");
			String command = sc.nextLine();

			command.trim();

			if (command.length() == 0) {
				continue;
			}

			if (command.equals("system exit")) {
				System.out.println("== 프로그램 종료 ==");
				break;

			} else if (command.equals("article list")) {

				if (articles.size() == 0) {
					System.out.println("등록된 게시글이 없습니다.");
					continue;
				}

				System.out.println("== 게시글 목록 ==");
				System.out.println("번호 / 제목 ");
				for (int i = 0; i < articles.size(); i++) {
					Article currentArticle = articles.get(i);
					System.out.printf(" %d / %s\n", currentArticle.id, currentArticle.title);
				}

			} else if (command.equals("article write")) {
				System.out.println("== 게시글 작성 ==");

				int id = lastId++;

				System.out.print("글 제목 : ");
				String title = sc.nextLine();

				System.out.print("글 내용 : ");
				String body = sc.nextLine();

				Article article = new Article(id, title, body);

				articles.add(article);

				System.out.printf("%d번 글이 등록되었습니다.\n", id);

			} else if (command.startsWith("article detail ")) {
				System.out.println("== 게시글 상세보기 ==");

				// 게시글 번호를 받아서 일치하는 번호 찾고 해당하는 정보를 출력한다.
				// 공백으로 구분하여 2번 인덱스로 지정된다.

				String[] commandBits = command.split(" ");
				int id = Integer.parseInt(commandBits[2]);

				Article foundArticle = null;

				for (int i = 0; i < articles.size(); i++) {

					Article currentArticle = articles.get(i);
					if (currentArticle.id == id) {
						foundArticle = currentArticle;
					}
				}

				// 해당 게시글이 없을 경우
				if (foundArticle == null) {
					System.out.printf("입력하신 %d번 게시글을 찾을 수 없습니다.\n", id);
					continue;
				}

				System.out.printf("번호 : %d\n", foundArticle.id);
				System.out.printf("제목 : %s\n", foundArticle.title);
				System.out.printf("내용 : %s\n", foundArticle.body);

			} else {
				System.out.printf("%s는 존재하지 않는 명령어입니다.\n", command);
				continue;
			}

		}
	}
}
