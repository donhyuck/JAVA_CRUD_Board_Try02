package board.controller;

import java.util.List;
import java.util.Scanner;

import board.container.Container;
import board.dto.Article;
import board.service.ArticleService;
import board.service.MemberService;
import board.util.Util;

public class ArticleController extends Controller {

	private Scanner sc;
	private String command;
	private String actionMethodName;
	private ArticleService articleService;
	private MemberService memberService;

	public ArticleController(Scanner sc) {
		this.sc = sc;
		articleService = Container.articleService;
		memberService = Container.memberService;
	}

	public void doAction(String command, String actionMethodName) {
		this.command = command;
		this.actionMethodName = actionMethodName;

		switch (actionMethodName) {
		case "list":
			showList();
			break;
		case "write":
			doWrite();
			break;
		case "modify":
			doModify();
			break;
		case "delete":
			doDelete();
			break;
		case "detail":
			showDetail();
			break;
		default:
			System.out.println("존재하지 않는 명령어입니다.");
			break;
		}
	}

	private void showList() {

		String searchKeyword = command.substring("article list".length()).trim();

		List<Article> forPrintArticles = articleService.getForPrintArticles(searchKeyword);

		if (forPrintArticles.size() == 0) {
			System.out.println("검색결과가 존재하지 않습니다.");
			return;
		}

		System.out.println("== 게시글 목록 ==");
		System.out.println("번호 /   작성일   / 작성자 / 제목 / 조회수");

		for (int i = forPrintArticles.size() - 1; i >= 0; i--) {

			Article currentArticle = forPrintArticles.get(i);

			String writerName = memberService.getMemberNameById(currentArticle.id);

			System.out.printf(" %d / %s / %s / %s / %d\n", currentArticle.id, currentArticle.regDate, writerName,
					currentArticle.title, currentArticle.hit);
		}

	}

	private void doWrite() {

		System.out.println("== 게시글 작성 ==");

		int id = articleService.getNewId();

		System.out.print("글 제목 : ");
		String title = sc.nextLine();

		System.out.print("글 내용 : ");
		String body = sc.nextLine();

		String regDate = Util.getCurrentDate();

		Article article = new Article(id, regDate, loginedMember.id, title, body);

		articleService.write(article);

		System.out.printf("%d번 글이 등록되었습니다.\n", id);

	}

	private void doModify() {

		String[] commandBits = command.split(" ");
		int id = Integer.parseInt(commandBits[2]);

		Article foundArticle = articleService.getArticleById(id);

		if (foundArticle == null) {
			System.out.printf("%d번 게시글을 찾을 수 없습니다.\n", id);
			return;
		}

		if (foundArticle.memberId != loginedMember.id) {
			System.out.println("해당 게시글에 대한 귄한이 없습니다.");
			return;
		}

		System.out.println("== 게시글 수정 ==");

		System.out.print("새 제목 : ");
		String title = sc.nextLine();

		System.out.print("새 내용 : ");
		String body = sc.nextLine();

		foundArticle.title = title;
		foundArticle.body = body;

		System.out.printf("%d번 게시글이 수정되었습니다.\n", id);

	}

	private void doDelete() {

		String[] commandBits = command.split(" ");
		int id = Integer.parseInt(commandBits[2]);

		Article foundArticle = articleService.getArticleById(id);

		if (foundArticle == null) {
			System.out.printf("%d번 게시글을 찾을 수 없습니다.\n", id);
			return;
		}

		if (foundArticle.memberId != loginedMember.id) {
			System.out.println("해당 게시글에 대한 귄한이 없습니다.");
			return;
		}

		articleService.remove(foundArticle);

		System.out.printf("%d번 게시글이 삭제되었습니다.\n", id);

	}

	private void showDetail() {

		String[] commandBits = command.split(" ");
		int id = Integer.parseInt(commandBits[2]);

		Article foundArticle = articleService.getArticleById(id);

		if (foundArticle == null) {
			System.out.printf("%d번 게시글을 찾을 수 없습니다.\n", id);
			return;
		}

		foundArticle.increaseHit();

		System.out.println("== 게시글 상세보기 ==");

		System.out.printf("번 호 : %d\n", foundArticle.id);
		System.out.printf("작성일 : %s\n", foundArticle.regDate);
		System.out.printf("작성자 : %d\n", foundArticle.memberId);
		System.out.printf("제 목 : %s\n", foundArticle.title);
		System.out.printf("내 용 : %s\n", foundArticle.body);
		System.out.printf("조회수 : %d\n", foundArticle.hit);

	}

	public void makeTestData() {
		System.out.println("테스트를 위한 게시글 데이터를 생성합니다.");

		articleService.write(new Article(articleService.getNewId(), Util.getCurrentDate(), 1, "test1", "test1", 11));
		articleService.write(new Article(articleService.getNewId(), Util.getCurrentDate(), 2, "test2", "test2", 21));
		articleService.write(new Article(articleService.getNewId(), Util.getCurrentDate(), 3, "test3", "test2", 31));
	}
}
