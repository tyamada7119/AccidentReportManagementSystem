import java.util.Calendar;

//XMLから取り込んだ事故情報（一部数値で指定されている状態）を
//①データベースに格納する形に変換する、②バリデーションチェックを行なうValueConvertクラス
//例】gender：
//		XMLから値引き出し(AccidentManageSystem.register())
//			　↓
//			★ 1 
//			★↓
//			★入力値に問題ある場合エラー
//			★↓
//			★男
//			　↓
//		データベースへ戻す(AccidentManagementSystem.register())
//		★部分がValueConvertクラスで行なわれる処理

public class ValueConvert {
	//XMLから取り込んだデータ
	public String familyName;
	public String firstName;
	public int age;
	public int genderX;
	public int positionX;
	
	public int year;
	public int month;
	public int date;
	public String dayOfTheWeek; //曜日。年月日をもとに割り出し
	
	public int time;
	
	public int situationID;
	public int typeID;
	
	public String abstractData;
	
	//データベースに格納するデータ
	//familyName, firstName, age, year, month, day, dayOfTheWeek, time, abstractDataはそのまま使う
	public int id;
	public String gender;
	public String position;
	
	//データの変換処理
	public String returnDayOfTheWeek(int year, int month, int date) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month,  date);
		switch (cal.get(Calendar.DAY_OF_WEEK)) {
			case Calendar.SUNDAY: 
				return "日";
			case Calendar.MONDAY:
				return "月";
			case Calendar.TUESDAY:
				return "火";
			case Calendar.WEDNESDAY:
				return "水";
			case Calendar.THURSDAY:
				return "木";
			case Calendar.FRIDAY:
				return "金";
			case Calendar.SATURDAY:
				return "土";
			default:
				return "入力された数字に誤りがあります。";
		}
	}
	
	public String convertGender(int gender) {
		switch (gender) {
			case 1: return "男"; 
			case 2: return "女";
			case 3: return "その他";
			default: return "入力された数字に誤りがあります。";
		}
	}
	
	public String convertPosition(int position) {
		switch (position) {
			case 1: return "学生"; 
			case 2: return "教員";
			case 3: return "事務";
			case 4: return "他職員";
			case 5: return "外部";
			default: return "入力された数字に誤りがあります。";
		}
	}
	
	public String convertDayOfTheWeek(int dayOfTheWeek) {
		switch(dayOfTheWeek) {
			case 1: return "月"; 
			case 2: return "火"; 
			case 3: return "水"; 
			case 4: return "木"; 
			case 5: return "金"; 
			case 6: return "土"; 
			case 7: return "日"; 
			default: return "入力された数字に誤りがあります。";
		}
	}
	
}