package board.controller;

public abstract class Controller {

	public abstract void makeTestData();

	public abstract void doAction(String command, String actionMethodName);

}
