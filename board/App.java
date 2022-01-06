package board;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
		System.out.println("== 프로그램 시작 ==");

		makeTestDate();

		Scanner sc = new Scanner(System.in);

		// memberController 구현
		MemberController memberController = new MemberController(sc, members);

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

			} else if (command.equals("member join")) {

				memberController.doJoin();

			} else if (command.startsWith("article list")) {

				if (articles.size() == 0) {
					System.out.println("등록된 게시글이 없습니다.");
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
						System.out.println("검색결과가 존재하지 않습니다.");
						continue;
					}

				}

				System.out.println("== 게시글 목록 ==");
				System.out.println("번호 /   작성일   / 제목 / 조회수");

				for (int i = forListArticles.size() - 1; i >= 0; i--) {

					Article currentArticle = forListArticles.get(i);
					System.out.printf(" %d / %s / %s / %d\n", currentArticle.id, currentArticle.regDate,
							currentArticle.title, currentArticle.hit);
				}

			} else if (command.equals("article write")) {
				System.out.println("== 게시글 작성 ==");

				int id = articles.size() + 1;

				System.out.print("글 제목 : ");
				String title = sc.nextLine();

				System.out.print("글 내용 : ");
				String body = sc.nextLine();

				String regDate = Util.getCurrentDate();

				Article article = new Article(id, regDate, title, body);

				articles.add(article);

				System.out.printf("%d번 글이 등록되었습니다.\n", id);

			} else if (command.startsWith("article modify ")) {

				String[] commandBits = command.split(" ");
				int id = Integer.parseInt(commandBits[2]);

				Article foundArticle = getArticleById(id);

				if (foundArticle == null) {
					System.out.printf("%d번 게시글을 찾을 수 없습니다.\n", id);
					continue;
				}

				System.out.println("== 게시글 수정 ==");

				System.out.print("새 제목 : ");
				String title = sc.nextLine();

				System.out.print("새 내용 : ");
				String body = sc.nextLine();

				foundArticle.title = title;
				foundArticle.body = body;

				System.out.printf("%d번 게시글이 수정되었습니다.\n", id);

			} else if (command.startsWith("article delete ")) {

				String[] commandBits = command.split(" ");
				int id = Integer.parseInt(commandBits[2]);

				int foundIndex = getArticleIndexById(id);

				if (foundIndex == -1) {
					System.out.printf("%d번 게시글을 찾을 수 없습니다.\n", id);
					continue;
				}

				articles.remove(foundIndex);

				System.out.printf("%d번 게시글이 삭제되었습니다.\n", id);

			} else if (command.startsWith("article detail ")) {

				String[] commandBits = command.split(" ");
				int id = Integer.parseInt(commandBits[2]);

				Article foundArticle = getArticleById(id);

				if (foundArticle == null) {
					System.out.printf("%d번 게시글을 찾을 수 없습니다.\n", id);
					continue;
				}

				foundArticle.increaseHit();

				System.out.println("== 게시글 상세보기 ==");

				System.out.printf("번 호 : %d\n", foundArticle.id);
				System.out.printf("작성일 : %s\n", foundArticle.regDate);
				System.out.printf("제 목 : %s\n", foundArticle.title);
				System.out.printf("내 용 : %s\n", foundArticle.body);
				System.out.printf("조회수 : %d\n", foundArticle.hit);

			} else {
				System.out.printf("%s는 존재하지 않는 명령어입니다.\n", command);
				continue;
			}

		}
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
		System.out.println("테스트를 위한 게시글 데이터를 생성합니다.");
		articles.add(new Article(1, Util.getCurrentDate(), "test1", "test1", 11));
		articles.add(new Article(2, Util.getCurrentDate(), "test2", "test2", 21));
		articles.add(new Article(3, Util.getCurrentDate(), "test3", "test2", 31));
	}

}
