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
				System.out.println("== 게시글 목록 ==");

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
