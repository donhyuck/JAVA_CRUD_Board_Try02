package board;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BoardMain {
	public static void main(String[] args) {
		System.out.println("== 프로그램 시작 ==");
		Scanner sc = new Scanner(System.in);

		// Article 로 게시글 정보를 연결한다.
		List<Article> articles = new ArrayList<>();

		// 게시글 작성마다 번호를 부여한다.
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
				// 0 부터 articles의 크기만큼 순회하면서 등록된 게시글의 번호와 제목 출력
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

			} else {
				System.out.printf("%s는 존재하지 않는 명령어입니다.\n", command);
				continue;
			}

		}
	}
}
