import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class AccidentManageSystem {
	
	public void register() {
		//エラー時は登録処理の初めに戻る
		boolean quit = false;
		while (!quit) {
			System.out.println("登録を行ないます。");
			System.out.println("フォルダ「src」内、「accident-xml-template.xml」ファイルをコピー後、事前に事故情報を記載してください。\n");
			
			System.out.println("用意した事故情報ファイルのパスを入力してください。");
			String input = new java.util.Scanner(System.in).nextLine();
			
			ValueConvert valueSet = new ValueConvert();
						
			//XMLをjavaへ取り込み
			try {
				FileInputStream is = new FileInputStream(Paths.get(input).toFile());	
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				
				//TODO XML文書の妥当性検証
//				try {
//					Schema schema = factory.getSchema();
//					factory.setSchema(schema);
//					Validator v = schema.newValidator();
//					v.validate(new StreamSource("accident.dtd"));
//				} catch (Exception e) {
//					System.out.println("バリデーションチェック時にエラー");
//					e.printStackTrace();
//				}
				
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.parse(is);
				
				XPath xpath = XPathFactory.newInstance().newXPath();
				try {
					//TODO 1つのXML内に複数の事故があった場合の処理
					//AR…AccidentReport要素の略
//					XPathExpression exprAR = xpath.compile("/accident-report/accident-unit");
//					NodeList nodeListAR = (NodeList) exprAR.evaluate(document, XPathConstants.NODESET);
//					for(int i = 0; i < nodeListAR.getLength(); i++) {
//						Node nodeAR = nodeListAR.item(i);
//						NodeList nodeListARChild = nodeAR.getChildNodes();
						
						//jはAR要素の何番目の子要素かを表す
//						for(int j = 0; j < nodeListARChild.getLength(); j ++) {
//							if (nodeListARChild.item(j).getLocalName() == "person-involved") {
//								NodeList nodeListPersonChild = nodeListARChild.item(j).getChildNodes();
//								for(int k = 0; k < nodeListPersonChild.getLength(); k ++) {
//									
//								}
//							}
//							if (nodeListARChild.item(j).getLocalName() == "accident-info") {
//								
//							}
//							
//							if (nodeListARChild.item(j).getLocalName() == "abstract") {
//								
//							}
//							
//						}
						
						//各XML要素をValueConvert.javaのフィールドに取り込み
						//XMLでの形式がデータベースに格納する形式と異なる場合は変換
						
						XPathExpression exprFamilyName = xpath.compile("/accident-report/accident-unit/person-involved/family-name");
						Element elementFamilyName = (Element) exprFamilyName.evaluate(document, XPathConstants.NODE);
						valueSet.familyName = elementFamilyName.getTextContent();
//						System.out.println(valueSet.familyName); 取り込み動作確認用
						
						XPathExpression exprFirstName = xpath.compile("/accident-report/accident-unit/person-involved/first-name");
						Element elementFirstName = (Element) exprFirstName.evaluate(document, XPathConstants.NODE);
						valueSet.firstName = elementFirstName.getTextContent();
//						System.out.println(valueSet.firstName);
						
						XPathExpression exprAge = xpath.compile("/accident-report/accident-unit/person-involved/age");
						Element elementAge = (Element) exprAge.evaluate(document, XPathConstants.NODE);
						valueSet.age = Integer.parseInt(elementAge.getTextContent());
//						System.out.println(valueSet.age);
						
						XPathExpression exprGender = xpath.compile("/accident-report/accident-unit/person-involved/gender");
						Element elementGender = (Element) exprGender.evaluate(document, XPathConstants.NODE);
						valueSet.genderX = Integer.parseInt(elementGender.getTextContent());
//						System.out.println(valueSet.genderX);
						valueSet.gender = valueSet.convertGender(valueSet.genderX);
//						System.out.println(valueSet.gender); 変換動作確認用
						
						XPathExpression exprPosition = xpath.compile("/accident-report/accident-unit/person-involved/position");
						Element elementPosition = (Element) exprPosition.evaluate(document, XPathConstants.NODE);
						valueSet.positionX = Integer.parseInt(elementPosition.getTextContent());
//						System.out.println(valueSet.positionX);
						valueSet.position = valueSet.convertPosition(valueSet.positionX);
//						System.out.println(valueSet.position);
						
						XPathExpression exprYear = xpath.compile("/accident-report/accident-unit/accident-info/occuring-date/year");
						Element elementYear = (Element) exprYear.evaluate(document, XPathConstants.NODE);
						valueSet.year = Integer.parseInt(elementYear.getTextContent());
//						System.out.println(valueSet.year);
						
						XPathExpression exprMonth = xpath.compile("/accident-report/accident-unit/accident-info/occuring-date/month");
						Element elementMonth = (Element) exprMonth.evaluate(document, XPathConstants.NODE);
						valueSet.month = Integer.parseInt(elementMonth.getTextContent());
//						System.out.println(valueSet.month);
						
						XPathExpression exprDate = xpath.compile("/accident-report/accident-unit/accident-info/occuring-date/date");
						Element elementDate = (Element) exprDate.evaluate(document, XPathConstants.NODE);
						valueSet.date = Integer.parseInt(elementDate.getTextContent());
//						System.out.println(valueSet.date);
						
						XPathExpression exprTime = xpath.compile("/accident-report/accident-unit/accident-info/occuring-date/time");
						Element elementTime = (Element) exprTime.evaluate(document, XPathConstants.NODE);
						valueSet.time = Integer.parseInt(elementTime.getTextContent());
//						System.out.println(valueSet.time);
						
						XPathExpression exprSituationID = xpath.compile("/accident-report/accident-unit/accident-info/situation");
						Element elementSituationID = (Element) exprSituationID.evaluate(document, XPathConstants.NODE);
						valueSet.situationID = Integer.parseInt(elementSituationID.getTextContent());
//						System.out.println(valueSet.situationID);
						
						XPathExpression exprTypeID = xpath.compile("/accident-report/accident-unit/accident-info/type");
						Element elementTypeID = (Element) exprTypeID.evaluate(document, XPathConstants.NODE);
						valueSet.typeID = Integer.parseInt(elementTypeID.getTextContent());
//						System.out.println(valueSet.typeID);
						
						XPathExpression exprAbstract = xpath.compile("/accident-report/accident-unit/abstract");
						Element elementAbstract = (Element) exprAbstract.evaluate(document, XPathConstants.NODE);
						valueSet.abstractData = elementAbstract.getTextContent();
//						System.out.println(valueSet.abstractData);
						
						valueSet.dayOfTheWeek = valueSet.returnDayOfTheWeek(valueSet.year, valueSet.month, valueSet.date);
//						System.out.println(valueSet.dayOfTheWeek);
//					}
					
				} catch (XPathExpressionException e) {
					System.out.println("式を評価できません。");
				}

				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.out.println("ファイルが正しくありません。（ファイル内容もしくはファイルパス）\n");
				continue;
			} catch (ParserConfigurationException e) {
				System.out.println("要求された構成を満たすDocumentBuilderを作成できません。");
			} catch (IOException e) {
				System.out.println("Documentオブジェクト作成時に入出力エラー");
			} catch (SAXException e) {
				System.out.println("Documentオブジェクト作成時に構文解析エラー");
			}
			
			
			//データベースへ格納
			Connection con = null;
			PreparedStatement pstmt = null;
			
			try {
				con = DriverManager.getConnection(
					"jdbc:mysql://localhost/accident_report",
					"root",
					"dpciz17iy9p"
				);
				
				//一つ目のdefaultがID、二つ目のdefaultがdelete
				pstmt = con.prepareStatement("INSERT INTO report VALUES (default, ?,?,?,?,?, ?,?,?,?,?, ?, ?, ?, default);");
				
				pstmt.setString(1, valueSet.familyName);
				pstmt.setString(2, valueSet.firstName);
				pstmt.setInt(3, valueSet.age);
				pstmt.setString(4, valueSet.gender);
				pstmt.setString(5, valueSet.position);
				
				pstmt.setInt(6, valueSet.year);
				pstmt.setInt(7, valueSet.month);
				pstmt.setInt(8, valueSet.date);
				pstmt.setString(9, valueSet.dayOfTheWeek);
				pstmt.setInt(10, valueSet.time);
				
				pstmt.setInt(11, valueSet.situationID);
				
				pstmt.setInt(12, valueSet.typeID);
				
				pstmt.setString(13, valueSet.abstractData);
				
				pstmt.executeUpdate();
				System.out.println("登録が完了しました。\n");
				quit = true;
			} catch(SQLException e) {
				e.printStackTrace();
			} finally {
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch(SQLException e) {
						e.printStackTrace();
					}
				}
				if (con != null) {
					try {
						con.close();
					} catch(SQLException e) {
						e.printStackTrace();
					}
				}
			}
		} //while文
	} //register()
	
	
	public void delete() {
		boolean quit = false;
		while (!quit) {
			System.out.println("削除を行ないます。");
			System.out.println("削除する事故の事故IDを入力してください。\n");
			int deleteID = new java.util.Scanner(System.in).nextInt();
			
			//削除実行
			Connection con = null;
			PreparedStatement pstmt = null;
			try {
				con = DriverManager.getConnection(
					"jdbc:mysql://localhost/accident_report",
					"root",
					"dpciz17iy9p"
				);
				
				pstmt = con.prepareStatement("UPDATE report SET deleteYN = 'Y' WHERE ID = ?");
				pstmt.setInt(1, deleteID);
				pstmt.executeUpdate();
				
				System.out.println("削除が完了しました。\n");
				
				quit = true;
				
			} catch(SQLException e) {
				e.printStackTrace();
			} finally {
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch(SQLException e) {
						e.printStackTrace();
					}
				}
				if (con != null) {
					try {
						con.close();
					} catch(SQLException e) {
						e.printStackTrace();
					}
				}
			}
		} //while文
	} //delete()
	
	
	public void viewAll() {
		System.out.println("全件表示を行ないます。\n");
		
		//全件表示実行
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(
				"jdbc:mysql://localhost/accident_report",
				"root",
				"dpciz17iy9p"
			);
			
			//SQL文の結果のResultSetオブジェクトを生成する
			pstmt = con.prepareStatement(
					"SELECT "
					+ "report.ID, report.`family-name`, report.`first-name`, report.age, report.gender, report.position, "
					+ "report.year, report.month, report.date, report.`day-of-the-week`, report.time, "
					+ "situation.situation, type.type, report.abstract "
					+ "FROM report "
					+ "LEFT JOIN situation USING (situationID) "
					+ "LEFT JOIN type USING (typeID) "
					+ "WHERE report.deleteYN = 'N';"
					);
			rs = pstmt.executeQuery();
			
			//ResultSetにおいて各行ごとに内容を取り込んで表示する。
			while(rs.next()) {
				int id = rs.getInt("ID");
				
				String familyName = rs.getString("family-name");
				String firstName = rs.getString("first-name");
				int age = rs.getInt("age");
				String gender = rs.getString("gender");
				String position = rs.getString("position");
				
				int year = rs.getInt("year");
				int month = rs.getInt("month");
				int date = rs.getInt("date");
				String dayOfTheWeek = rs.getString("day-of-the-week");
				int time = rs.getInt("time");
				
				String situation = rs.getString("situation");
				String type = rs.getString("type");
				
				String abstractData = rs.getString("abstract"); 
				
				System.out.println("事故ID：" + id);
				System.out.println("氏名：" + familyName + " " + firstName);
				System.out.println("年齢：" + age);
				System.out.println("性別：" + gender);
				System.out.println("身分：" + position);
				System.out.println("発生日時：" + year + "年" + month + "月" + date + "日（" + dayOfTheWeek + "）" + time + "時");
				System.out.println("事故状況：" + situation);
				System.out.println("事故分類：" + type);
				System.out.println("事故概要：" + abstractData);
			}
			
			System.out.println("全件表示が完了しました。\n");
						
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
	} //viewAll()
	
	
	public void viewConditional() {		
		System.out.println("絞込み表示を行ないます。");
		
		ValueConvert valueConvert = new ValueConvert();
		
		//以下①で絞込みがあった場合trueにし、trueの項目を②でSQL文に追加するための変数
		boolean idYN = false;
		boolean familyNameYN = false;
		boolean firstNameYN = false;
		boolean ageYN = false;
		boolean genderYN = false;
		boolean positionYN = false;
		boolean yearYN = false;
		boolean monthYN = false;
		boolean dateYN = false;
		boolean dayOfTheWeekYN = false;
		boolean timeYN = false;
		boolean situationYN = false;
		boolean typeYN = false;
		
		//以下①で絞込み内容を入力し、②でその項目をWHERE句に付け足すための変数
		String whereClauseId = null;
		String whereClauseFamilyName = null;
		String whereClauseFirstName = null;
		String whereClauseAge = null;
		String whereClauseGender = null;
		String whereClausePosition = null;
		String whereClauseYear = null;
		String whereClauseMonth = null;
		String whereClauseDate = null;
		String whereClauseDayOfTheWeek = null;
		String whereClauseTime = null;
		String whereClauseSituation = null;
		String whereClauseType = null;
		
		
		//①絞込み内容の入力
		boolean quit = false;
		while (!quit) {
			System.out.println("\n絞込み項目を半角数字で一つ指定してください。");
			System.out.println(
				"1:事故ID　2:名前(姓)　3:名前(名)　4:年齢　5:性別　6:身分　7:年　8:月　9:日　10:曜日　11:時間　12:事故状況　13:事故分類"
			);
			int input = new java.util.Scanner(System.in).nextInt();
			
			switch (input) {
				case 1:
					if (idYN == true) {
						System.out.println("この項目はすでに指定済みです。");
						break;
					} else {
						System.out.println("絞込み内容を入力してください。（完全一致）");
						whereClauseId = new java.util.Scanner(System.in).nextLine();
						//TODO 入力のバリデーションチェック
						idYN = true;
						break;
					}
				case 2:	
					if (familyNameYN == true) {
						System.out.println("この項目はすでに指定済みです。");
						break;
					} else {
						System.out.println("絞込み内容を半角数字で入力してください。");
						whereClauseFamilyName = new java.util.Scanner(System.in).nextLine();
						familyNameYN = true;
						break;
					}
				case 3:
					if (firstNameYN == true) {
						System.out.println("この項目はすでに指定済みです。");
						break;
					} else {
						System.out.println("絞込み内容を半角数字で入力してください。");
						whereClauseFirstName = new java.util.Scanner(System.in).nextLine();
						firstNameYN = true;
						break;
					}
				case 4:
					if (ageYN == true) {
						System.out.println("この項目はすでに指定済みです。");
						break;
					} else {
						System.out.println("絞込み内容を半角数字で入力してください。");
						whereClauseAge = new java.util.Scanner(System.in).nextLine();
						ageYN = true;
						break;
					}
				case 5:
					if (genderYN == true) {
						System.out.println("この項目はすでに指定済みです。");
						break;
					} else {
						System.out.println("絞込み内容を半角数字で入力してください。1:男　2:女　3:その他");
						int inputGender = new java.util.Scanner(System.in).nextInt();
						whereClauseGender = valueConvert.convertGender(inputGender);
						genderYN = true;
						break;
					}
				case 6:
					if (positionYN == true) {
						System.out.println("この項目はすでに指定済みです。");
						break;
					} else {
						System.out.println("絞込み内容を半角数字で入力してください。1:学生　2:教員　3:事務　4:他職員　5:外部");
						int inputPosition = new java.util.Scanner(System.in).nextInt();
						whereClausePosition = valueConvert.convertPosition(inputPosition);
						positionYN = true;
						break;
					}
				case 7:
					if (yearYN == true) {
						System.out.println("この項目はすでに指定済みです。");
						break;
					} else {
						System.out.println("絞込み内容を入力してください。（西暦 半角数字）");
						whereClauseYear = new java.util.Scanner(System.in).nextLine();
						yearYN = true;
						break;
					}
				case 8:
					if (monthYN == true) {
						System.out.println("この項目はすでに指定済みです。");
						break;
					} else {
						System.out.println("絞込み内容を入力してください。（半角数字）");
						whereClauseMonth = new java.util.Scanner(System.in).nextLine();
						monthYN = true;
						break;
					}
				case 9:
					if (dateYN == true) {
						System.out.println("この項目はすでに指定済みです。");
						break;
					} else {
						System.out.println("絞込み内容を入力してください。（半角数字）");
						whereClauseDate = new java.util.Scanner(System.in).nextLine();
						dateYN = true;
						break;
					}
				case 10:
					if (dayOfTheWeekYN == true) {
						System.out.println("この項目はすでに指定済みです。");
						break;
					} else {
						System.out.println("絞込み内容を半角数字で入力してください。1:月　2:火　3:水　4:木　5:金　6:土　7:日");
						int inputDayOfTheWeek = new java.util.Scanner(System.in).nextInt();
						whereClauseDate = valueConvert.convertDayOfTheWeek(inputDayOfTheWeek);
						dayOfTheWeekYN = true;
						break;
					}
				case 11:
					if (timeYN == true) {
						System.out.println("この項目はすでに指定済みです。");
						break;
					} else {
						System.out.println("絞込み内容を半角数字で入力してください。（24時間表示）");
						whereClauseTime = new java.util.Scanner(System.in).nextLine();
						timeYN = true;
						break;
					}
				case 12:
					if (situationYN == true) {
						System.out.println("この項目はすでに指定済みです。");
						break;
					} else {
						System.out.println("絞込み内容を半角数字で入力してください。1:実験実習中　2:日常生活中　3:通勤通学中　4:サークル活動中");
						int inputSituation = new java.util.Scanner(System.in).nextInt();
						whereClauseSituation = valueConvert.convertSituation(inputSituation);
						situationYN = true;
						break;
					}
				case 13:
					if (typeYN == true) {
						System.out.println("この項目はすでに指定済みです。");
						break;
					} else {
						System.out.println("絞込み内容を半角数字で入力してください。1:はさまれ、巻き込まれ　2:切れ、こすれ　3:墜落、転落　4:交通事故　5:飛来、落下　6:激突　7:転倒　8:高温、低温物との接触　9:有害物との接触");
						int inputType = new java.util.Scanner(System.in).nextInt();
						whereClauseType = valueConvert.convertType(inputType);
						typeYN = true;
						break;
					}
				default:
					System.out.println("入力された数字に誤りがあります。");
			}
			
			//while文は下記入力で誤りがあった場合に、項目選択に戻らず下記質問を繰り返すために追加
			boolean quit2 = false;
			while (!quit2) {
				System.out.println("更に絞込み項目を指定しますか？ 1:はい　2:いいえ");
				int inputQuit = new java.util.Scanner(System.in).nextInt();
				switch (inputQuit) {
					case 1:
						quit2 = true;
						break;
					case 2:
						quit2 = true;
						quit = true;
						break;
					default:
						System.out.println("入力された数字に誤りがあります。");
				}
			}
		}
		
		
		//②SQL文のWHERE句に付け加える絞込み内容を作る
		
		//最終的にWHERE句に付け加えるもの
		String whereClauseFinal = "";
		
		//TODO 配列使えばもっとシンプルにできそう（絞込み内容のStringを配列に入れていって、順に取り出し
		//→項目ごとにreport.〇〇(カラム名)を変えないといけないから使えないかも）
		//→`family-name`, `first-name`, age, gender,,,みたいなカラムの配列を作れば行けるかもしれんけど、
		//複雑になってわかりにくそうな割に、そこまで短くもならなそう
		if (idYN == true) {
			whereClauseFinal = whereClauseFinal + " AND report.ID = " + whereClauseId;
		}
		if (familyNameYN == true) {
			whereClauseFinal = whereClauseFinal + " AND report.`family-name` = '" + whereClauseFamilyName + "'";
		}
		if (firstNameYN == true) {
			whereClauseFinal = whereClauseFinal + " AND report.`first-name` = '" + whereClauseFirstName + "'";
		}
		if (ageYN == true) {
			whereClauseFinal = whereClauseFinal + " AND report.age = " + whereClauseAge;
		}
		if (genderYN == true) {
			whereClauseFinal = whereClauseFinal + " AND report.gender = '" + whereClauseGender + "'";
		}
		if (positionYN == true) {
			whereClauseFinal = whereClauseFinal + " AND report.position = '" + whereClausePosition + "'";
		}
		if (yearYN == true) {
			whereClauseFinal = whereClauseFinal + " AND report.year = " + whereClauseYear;
		}
		if (monthYN == true) {
			whereClauseFinal = whereClauseFinal + " AND report.month = " + whereClauseMonth;
		}
		if (dateYN == true) {
			whereClauseFinal = whereClauseFinal + " AND report.date = " + whereClauseDate;
		}
		if (dayOfTheWeekYN == true) {
			whereClauseFinal = whereClauseFinal + " AND report.`day-of-the-week` = '" + whereClauseDayOfTheWeek + "'";
		}
		if (situationYN == true) {
			whereClauseFinal = whereClauseFinal + " AND situation.situation = '" + whereClauseSituation + "'";
		}
		if (typeYN == true) {
			whereClauseFinal = whereClauseFinal + " AND report.type = '" + whereClauseType + "'";
		}
		
		
		//③絞込み内容のSQL文を使ってデータ取り出し・表示
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DriverManager.getConnection(
				"jdbc:mysql://localhost/accident_report",
				"root",
				"dpciz17iy9p"
			);
			
			//SQL文の結果のResultSetオブジェクトを生成する
			pstmt = con.prepareStatement(
					"SELECT "
					+ "report.ID, report.`family-name`, report.`first-name`, report.age, report.gender, report.position, "
					+ "report.year, report.month, report.date, report.`day-of-the-week`, report.time, "
					+ "situation.situation, type.type, report.abstract "
					+ "FROM report "
					+ "LEFT JOIN situation USING (situationID) "
					+ "LEFT JOIN type USING (typeID) "
					+ "WHERE report.deleteYN = 'N'"
					+ whereClauseFinal
					+ ";"
					);
			rs = pstmt.executeQuery();
			
			//各行ごとに内容を取り込んで表示する。
			while(rs.next()) {
				int id = rs.getInt("ID");
				
				String familyName = rs.getString("family-name");
				String firstName = rs.getString("first-name");
				int age = rs.getInt("age");
				String gender = rs.getString("gender");
				String position = rs.getString("position");
				
				int year = rs.getInt("year");
				int month = rs.getInt("month");
				int date = rs.getInt("date");
				String dayOfTheWeek = rs.getString("day-of-the-week");
				int time = rs.getInt("time");
				
				String situation = rs.getString("situation");
				String type = rs.getString("type");
				
				String abstractData = rs.getString("abstract"); 
				
				System.out.println("事故ID：" + id);
				System.out.println("氏名：" + familyName + " " + firstName);
				System.out.println("年齢：" + age);
				System.out.println("性別：" + gender);
				System.out.println("身分：" + position);
				System.out.println("発生日時：" + year + "年" + month + "月" + date + "日（" + dayOfTheWeek + "）" + time + "時");
				System.out.println("事故状況：" + situation);
				System.out.println("事故分類：" + type);
				System.out.println("事故概要：" + abstractData);
			}
			
			System.out.println("絞込み表示が完了しました。\n");
						
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}