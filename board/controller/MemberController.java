package board.controller;

import java.util.Scanner;

import board.container.Container;
import board.dto.Member;
import board.service.MemberService;
import board.util.Util;

public class MemberController extends Controller {

	private Scanner sc;
	private String command;
	private String actionMethodName;
	private MemberService memberService;

	public MemberController(Scanner sc) {
		this.sc = sc;
		memberService = Container.memberService;
	}

	public void doAction(String command, String actionMethodName) {
		this.command = command;
		this.actionMethodName = actionMethodName;

		switch (actionMethodName) {
		case "join":
			doJoin();
			break;
		case "login":
			doLogin();
			break;
		case "logout":
			doLogout();
			break;
		case "whoami":
			showWhoAmI();
			break;
		default:
			System.out.println("존재하지 않는 명령어입니다.");
			break;
		}

	}

	private void doLogout() {

		System.out.printf("%s님 로그아웃되었습니다.\n", loginedMember.name);
		loginedMember = null;

	}

	private void showWhoAmI() {

		if (loginedMember == null) {
			System.out.println("로그아웃 상태입니다.");
			return;
		}

		System.out.println("== 현재 상태보기 ==");
		System.out.printf("로그인 아이디 : %s\n", loginedMember.loginId);
		System.out.printf("이름 : %s\n", loginedMember.name);

	}

	private void doLogin() {

		System.out.println("== 회원 로그인 ==");

		String loginId;
		String loginPw;
		Member member;
		int blockCnt = 0;

		while (true) {

			System.out.print("로그인 아이디 : ");
			loginId = sc.nextLine();

			member = memberService.getMemberByLoginId(loginId);

			if (member == null) {
				System.out.println("비밀번호를 확인해주세요.");
				blockCnt++;

				if (blockCnt > 3) {
					System.out.println("입력횟수가 초과되었습니다.");
					return;
				}
				continue;
			}

			break;
		}

		blockCnt = 0;
		while (true) {
			System.out.print("로그인 비밀번호 : ");
			loginPw = sc.nextLine();

			if (member.loginPw.equals(loginPw) == false) {
				System.out.println("비밀번호를 확인해주세요.");
				blockCnt++;

				if (blockCnt > 3) {
					System.out.println("입력횟수가 초과되었습니다.");
					return;
				}
				continue;
			}

			break;
		}

		loginedMember = member;
		System.out.printf("%s님 로그인되었습니다.\n", loginedMember.name);

	}

	private void doJoin() {

		System.out.println("== 회원 가입 ==");

		String loginId = null;
		int blockCnt = 0;

		while (true) {
			System.out.print("로그인 아이디 : ");
			loginId = sc.nextLine();

			if (loginId.length() == 0) {
				continue;
			}

			if (isJoinableLoginedId(loginId) == false) {
				System.out.printf("%s는 이미 사용중인 아이디입니다.\n", loginId);
				blockCnt++;

				if (blockCnt > 3) {
					System.out.println("입력횟수가 초과되었습니다.");
					return;
				}

				continue;
			}

			break;
		}

		String loginPw = null;
		String loginPwConfirm = null;
		blockCnt = 0;

		while (true) {
			System.out.print("로그인 비밀번호 : ");
			loginPw = sc.nextLine();

			if (loginPw.length() == 0) {
				continue;
			}

			System.out.print("로그인 비밀번호 확인 : ");
			loginPwConfirm = sc.nextLine();

			if (loginPwConfirm.equals(loginPw) == false) {
				System.out.println("비밀번호를 다시입력해주세요.");
				blockCnt++;

				if (blockCnt > 3) {
					System.out.println("입력횟수가 초과되었습니다.");
					return;
				}

				continue;
			}

			break;
		}

		String name;
		while (true) {
			System.out.print("이름 : ");
			name = sc.nextLine();

			if (name.length() == 0) {
				continue;
			}

			break;
		}

		int id = memberService.getNewId();
		String regDate = Util.getCurrentDate();

		Member member = new Member(id, regDate, loginId, loginPw, name);

		memberService.join(member);

		System.out.printf("%s님 회원가입이 완료되었습니다.\n", name);

	}

	private boolean isJoinableLoginedId(String loginId) {

		int index = memberService.getMemberIndexByLoginId(loginId);

		if (index == -1) {
			return true;
		}

		return false;
	}

	public void makeTestData() {
		System.out.println("테스트를 위한 회원 데이터를 생성합니다.");

		memberService.join(new Member(memberService.getNewId(), Util.getCurrentDate(), "admin", "admin", "관리자"));
		memberService.join(new Member(memberService.getNewId(), Util.getCurrentDate(), "test1", "test1", "홍길동"));
		memberService.join(new Member(memberService.getNewId(), Util.getCurrentDate(), "test2", "test2", "성춘향"));
	}

}
