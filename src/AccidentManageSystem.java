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
			System.out.println("フォルダ「src」内、XMLファイルをコピー後、事前に事故情報を記載してください。\n");
			
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
				System.out.println("ファイルが正しくありません。\n");
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
					"ei9ytf7121"
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
					"ei9ytf7121"
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
		}
	}
	
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
				"ei9ytf7121"
			);
			
			//SQL文の結果のResultSetオブジェクトを生成する
			pstmt = con.prepareStatement("SELECT "
					+ "report.family-name, report.first-name, report.age, report.gender, report.position, "
					+ "report.year, report.month, report.date, report.day-of-the-week, report.time, "
					+ "situation.situation, type.type, report.abstract "
					+ "FROM report "
					+ "WHERE report.deleteYN = 'N' "
					+ "LEFT JOIN situation USING (situationID) "
					+ "LEFT JOIN type USING (typeID);");
			rs = pstmt.executeQuery();
			
			//各行ごとに内容を取り込んで表示する。
			while(rs.next()) {
				int id = rs.getInt("ID");
				
				String familyName = rs.getString("family-name");
				String firstName = rs.getString("first-name");
				int age = rs.getInt("age");
				String gender = rs.getString("gender");
				String position = rs.getString("postition");
				
				int year = rs.getInt("year");
				int month = rs.getInt("month");
				int date = rs.getInt("date");
				String dayOfTheWeek = rs.getString("day-of-the-week");
				
				String situation = rs.getString("situation");
				String type = rs.getString("type");
				
				String abstractData = rs.getString("abstract"); 
				
				System.out.println("事故ID：" + id);
				System.out.println("氏名：" + familyName + " " + firstName);
				System.out.println("年齢：" + age);
				System.out.println("性別：" + gender);
				System.out.println("身分：" + position);
				System.out.println("発生日時：" + year + "年" + month + "月" + date + "日（" + dayOfTheWeek + "）");
				System.out.println("事故状況：" + situation);
				System.out.println("事故分類：" + type);
				System.out.println("事故概要：" + abstractData + "\n");
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
	}
	
	public void viewConditional() {
		System.out.println("絞込み表示を行ないます。\n");
	}
}