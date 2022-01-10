package board.controller;

import java.util.Scanner;

import board.container.Container;
import board.service.ExportService;

public class ExportController extends Controller {

	private Scanner sc;
	private String command;
	private String actionMethodName;
	private ExportService exportService;

	public ExportController(Scanner sc) {
		this.sc = sc;
		exportService = Container.exportService;
	}

	public void doAction(String command, String actionMethodName) {
		this.command = command;
		this.actionMethodName = actionMethodName;

		switch (actionMethodName) {
		case "html":
			dohtml();
			break;
		default:
			System.out.println("�������� �ʴ� ��ɾ��Դϴ�.");
			break;
		}

	}

	private void dohtml() {
		System.out.println("html ������ �����մϴ�.");
	}

	@Override
	public void makeTestData() {

	}
}
