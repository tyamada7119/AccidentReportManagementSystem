public class Main{
	public static void main(String[] args) {	
		AccidentManageSystem a = new AccidentManageSystem();
		boolean quit = false;
		
		while(!quit) {
			System.out.println("\n操作を入力してください。\n(1:登録　2:削除　3:表示(全件表示・絞込み表示)　4:終了)\n");
			int operation = new java.util.Scanner(System.in).nextInt();
			
			switch(operation) {
				case 1:
					a.register();
					break;
				case 2:
					a.delete();
					break;
				case 3:
					//表示方法の指定。入力値が誤った値の場合は繰り返し。
					boolean quitView = false;
					while(!quitView) {
						System.out.println("表示を行ないます。\n");
						System.out.println("希望する表示方法を選択してください。");
						System.out.println("1:全件表示　2:絞込み表示");
						int view = new java.util.Scanner(System.in).nextInt();
						switch(view) {
							case 1:
								a.viewAll();
								quitView = true;
								break;
							case 2:
								a.viewConditional();
								quitView = true;
								break;
							default:
								System.out.println("正しい値を入力してください。\n");
						}
					}
					break;
				case 4:
					System.out.println("操作を終了します。");
					quit = true;
					break;
				default:
					System.out.println("正しい値を入力してください。\n");
			}
		}
	}
}