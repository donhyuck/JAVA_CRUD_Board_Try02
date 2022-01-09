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
			System.out.println("�������� �ʴ� ��ɾ��Դϴ�.");
			break;
		}

	}

	private void doLogout() {

		System.out.printf("%s�� �α׾ƿ��Ǿ����ϴ�.\n", loginedMember.name);
		loginedMember = null;

	}

	private void showWhoAmI() {

		if (loginedMember == null) {
			System.out.println("�α׾ƿ� �����Դϴ�.");
			return;
		}

		System.out.println("== ���� ���º��� ==");
		System.out.printf("�α��� ���̵� : %s\n", loginedMember.loginId);
		System.out.printf("�̸� : %s\n", loginedMember.name);

	}

	private void doLogin() {

		System.out.println("== ȸ�� �α��� ==");

		String loginId;
		String loginPw;
		Member member;
		int blockCnt = 0;

		while (true) {

			System.out.print("�α��� ���̵� : ");
			loginId = sc.nextLine();

			member = memberService.getMemberByLoginId(loginId);

			if (member == null) {
				System.out.println("��й�ȣ�� Ȯ�����ּ���.");
				blockCnt++;

				if (blockCnt > 3) {
					System.out.println("�Է�Ƚ���� �ʰ��Ǿ����ϴ�.");
					return;
				}
				continue;
			}

			break;
		}

		blockCnt = 0;
		while (true) {
			System.out.print("�α��� ��й�ȣ : ");
			loginPw = sc.nextLine();

			if (member.loginPw.equals(loginPw) == false) {
				System.out.println("��й�ȣ�� Ȯ�����ּ���.");
				blockCnt++;

				if (blockCnt > 3) {
					System.out.println("�Է�Ƚ���� �ʰ��Ǿ����ϴ�.");
					return;
				}
				continue;
			}

			break;
		}

		loginedMember = member;
		System.out.printf("%s�� �α��εǾ����ϴ�.\n", loginedMember.name);

	}

	private void doJoin() {

		System.out.println("== ȸ�� ���� ==");

		String loginId = null;
		int blockCnt = 0;

		while (true) {
			System.out.print("�α��� ���̵� : ");
			loginId = sc.nextLine();

			if (loginId.length() == 0) {
				continue;
			}

			if (isJoinableLoginedId(loginId) == false) {
				System.out.printf("%s�� �̹� ������� ���̵��Դϴ�.\n", loginId);
				blockCnt++;

				if (blockCnt > 3) {
					System.out.println("�Է�Ƚ���� �ʰ��Ǿ����ϴ�.");
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
			System.out.print("�α��� ��й�ȣ : ");
			loginPw = sc.nextLine();

			if (loginPw.length() == 0) {
				continue;
			}

			System.out.print("�α��� ��й�ȣ Ȯ�� : ");
			loginPwConfirm = sc.nextLine();

			if (loginPwConfirm.equals(loginPw) == false) {
				System.out.println("��й�ȣ�� �ٽ��Է����ּ���.");
				blockCnt++;

				if (blockCnt > 3) {
					System.out.println("�Է�Ƚ���� �ʰ��Ǿ����ϴ�.");
					return;
				}

				continue;
			}

			break;
		}

		String name;
		while (true) {
			System.out.print("�̸� : ");
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

		System.out.printf("%s�� ȸ�������� �Ϸ�Ǿ����ϴ�.\n", name);

	}

	private boolean isJoinableLoginedId(String loginId) {

		int index = memberService.getMemberIndexByLoginId(loginId);

		if (index == -1) {
			return true;
		}

		return false;
	}

	public void makeTestData() {
		System.out.println("�׽�Ʈ�� ���� ȸ�� �����͸� �����մϴ�.");

		memberService.join(new Member(memberService.getNewId(), Util.getCurrentDate(), "admin", "admin", "������"));
		memberService.join(new Member(memberService.getNewId(), Util.getCurrentDate(), "test1", "test1", "ȫ�浿"));
		memberService.join(new Member(memberService.getNewId(), Util.getCurrentDate(), "test2", "test2", "������"));
	}

}
