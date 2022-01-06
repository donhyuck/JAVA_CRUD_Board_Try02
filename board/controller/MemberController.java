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

		System.out.println("== ȸ�� ���� ==");

		String loginId = null;

		while (true) {
			System.out.print("�α��� ���̵� : ");
			loginId = sc.nextLine();

			if (isJoinableLoginedId(loginId) == false) {
				System.out.printf("%s�� �̹� ������� ���̵��Դϴ�.\n", loginId);
				continue;
			}

			break;
		}

		String loginPw = null;
		String loginPwConfirm = null;

		while (true) {
			System.out.print("�α��� ��й�ȣ : ");
			loginPw = sc.nextLine();

			System.out.print("�α��� ��й�ȣ Ȯ�� : ");
			loginPwConfirm = sc.nextLine();

			if (loginPwConfirm.equals(loginPw) == false) {
				System.out.println("��й�ȣ�� �ٽ��Է����ּ���.");
				continue;
			}

			break;
		}

		System.out.print("�̸� : ");
		String name = sc.nextLine();

		int id = members.size() + 1;
		String regDate = Util.getCurrentDate();

		Member member = new Member(id, regDate, loginId, loginPw, name);
		members.add(member);

		System.out.printf("%s�� ȸ�������� �Ϸ�Ǿ����ϴ�.\n", name);

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
		System.out.println("�׽�Ʈ�� ���� ȸ�� �����͸� �����մϴ�.");
		members.add(new Member(1, Util.getCurrentDate(), "admin", "admin", "������"));
		members.add(new Member(2, Util.getCurrentDate(), "test1", "test1", "ȫ�浿"));
		members.add(new Member(3, Util.getCurrentDate(), "test2", "test2", "������"));
	}

}
