package board.controller;

import java.util.List;
import java.util.Scanner;

import board.dto.Member;
import board.util.Util;

public class MemberController extends Controller {

	private Scanner sc;
	private List<Member> members;
	private String command;
	private String actionMethodName;

	public MemberController(Scanner sc, List<Member> members) {
		this.sc = sc;
		this.members = members;
	}

	public void doAction(String command, String actionMethodName) {
		this.command = command;
		this.actionMethodName = actionMethodName;

		switch (actionMethodName) {
		case "join":
			doJoin();
			break;
		}

	}

	public void doJoin() {

		System.out.println("== 회원 가입 ==");

		String loginId = null;

		while (true) {
			System.out.print("로그인 아이디 : ");
			loginId = sc.nextLine();

			if (isJoinableLoginedId(loginId) == false) {
				System.out.printf("%s는 이미 사용중인 아이디입니다.\n", loginId);
				continue;
			}

			break;
		}

		String loginPw = null;
		String loginPwConfirm = null;

		while (true) {
			System.out.print("로그인 비밀번호 : ");
			loginPw = sc.nextLine();

			System.out.print("로그인 비밀번호 확인 : ");
			loginPwConfirm = sc.nextLine();

			if (loginPwConfirm.equals(loginPw) == false) {
				System.out.println("비밀번호를 다시입력해주세요.");
				continue;
			}

			break;
		}

		System.out.print("이름 : ");
		String name = sc.nextLine();

		int id = members.size() + 1;
		String regDate = Util.getCurrentDate();

		Member member = new Member(id, regDate, loginId, loginPw, name);
		members.add(member);

		System.out.printf("%s님 회원가입이 완료되었습니다.\n", name);

	}

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

	private boolean isJoinableLoginedId(String loginId) {

		int index = getMemberIndexByLoginId(loginId);

		if (index == -1) {
			return true;
		}

		return false;
	}

	public void makeTestData() {
		System.out.println("테스트를 위한 회원 데이터를 생성합니다.");
		members.add(new Member(1, Util.getCurrentDate(), "admin", "admin", "관리자"));
		members.add(new Member(2, Util.getCurrentDate(), "test1", "test1", "홍길동"));
		members.add(new Member(3, Util.getCurrentDate(), "test2", "test2", "성춘향"));
	}

}
