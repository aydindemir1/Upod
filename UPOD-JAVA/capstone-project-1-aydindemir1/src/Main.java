import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
	static boolean isOperator(char ch) {
		if (ch != '+' && ch != '-' && ch != '*' && ch != '/')
			return false;
		return true;
	}

	static double Calculate(String denklem) throws Exception {

		ArrayList<String> numbers = new ArrayList<String>();

		ArrayList<Character> operators = new ArrayList<Character>();

		String s1 = "";
		for (int i = 0; i < denklem.length(); i++) {
			if (isOperator(denklem.charAt(i))) {
				if (s1.length() > 0) {
					numbers.add(s1);
					s1 = "";
				}
				operators.add(denklem.charAt(i));
			} else {
				s1 += denklem.charAt(i);
				if (i == denklem.length() - 1) {
					numbers.add(s1);
					s1 = "";
				}
			}
		}

		numbers = (ArrayList<String>) numbers.stream().map(element -> {
			if (element.length() > 3) {
				String el = element.substring(0, 3);
				boolean ifItsAnTrigonal = el.equals("cos") || el.equals("sin") || el.equals("tan") || el.equals("cot");

				if (ifItsAnTrigonal) {
					String Angle = element.substring(4, element.indexOf(")"));

					double AngleD = Double.parseDouble(Angle);
					double elm = 0.0f;

					switch (el) {
					case "cos":
						elm = Math.round(Math.cos(Math.toRadians((AngleD))) * 100) / 100.0f;
						break;
					case "sin":
						elm = Math.round(Math.sin(Math.toRadians((AngleD))) * 100) / 100.0f;
						break;
					case "tan":
						elm = Math.round(Math.tan(Math.toRadians((AngleD))) * 100) / 100.0f;
						break;
					case "cot":
						elm = Math.round(Math.atan(Math.toRadians((AngleD))) * 100) / 100.0f;
						break;

					}

					element = String.valueOf(elm);

				}

				if (el.equals("pow")) {
					int index = element.indexOf(",");
					double number1 = Double.parseDouble(element.substring(4, index));
					double number2 = Double.parseDouble(element.substring(index + 1, element.length() - 1));
					element = String.valueOf(Math.pow(number1, number2));
				}
				if (element.length() > 4)
					if (element.substring(0, 4).equals("sqrt")) {
						System.out.println(element.substring(0, 4));

						String numberS = element.substring(5, element.indexOf(")"));
						double Number = Double.parseDouble(numberS);
						double sqrt = Math.sqrt(Number);
						element = String.valueOf(sqrt);
					}
			}
			return element;
		}).collect(Collectors.toList());

		double result = 0;
		int hasMinus = 'n';
		if (numbers.size() == operators.size()) {
			if (operators.get(0) == '-') {
				operators.remove(0);
				hasMinus = 'y';
			}
		}
		for(int j=0;j<denklem.length();j++)
		for (int i = 0; i < operators.size(); i++) {

			char op = operators.get(i);

			if (op == '*') {
				
				result = Double.parseDouble(numbers.get(i)) * Double.parseDouble(numbers.get(i + 1));
				
				numbers.remove(i);
				numbers.remove(i);
				operators.remove(i);

				numbers.add(i, String.valueOf(result));
				result = 0;
			}
			if (op == '/') {

				result = Double.parseDouble(numbers.get(i)) / Double.parseDouble(numbers.get(i + 1));

				numbers.remove(i);
				numbers.remove(i);
				operators.remove(i);
				numbers.add(i, String.valueOf(result));
				result = 0;
			}
		}

		result = Double.parseDouble(numbers.get(0));

		if (hasMinus == 'y') {
			result = result * -1;

		}

		double sum = 0;
		if (numbers.size() == operators.size()) {
			for (int i = 0; i < operators.size(); i++) {
				char op = operators.get(i);
				if (op == '-') {
					String val = numbers.get(i);
					numbers.remove(i);

					numbers.add(i, String.valueOf(-1 * Double.parseDouble(val)));
				}
			}
			for (int i = 0; i < numbers.size(); i++) {
				sum += Double.parseDouble(numbers.get(i));
			}
			return sum;
		}

		if (numbers.size() > operators.size())
			for (int i = 0; i < operators.size(); i++) {

				char op = operators.get(i);

				if (op == '+') {

					result += Double.parseDouble(numbers.get(i + 1));
				}
				if (op == '-') {

					result -= Double.parseDouble(numbers.get(i + 1));

				}
			}

		return result;
	}

	public static void main(String[] args) throws Exception {
		String denklem = "";

		System.out.println("* Gelişmiş Hesap Makinesi *");
		System.out.println("Toplama : + | Çıkarma : - | Çarpma : * | Bölme : / | ");
		System.out.println("Karekök : sqrt(x) | Üs Alma : pow(x,y) | Sin : sin(x) | Cos : cos(x) | Tan : tan(x)");
		//System.out.println("Çıkmak için : exit z ye basar misiniz? veya entere basiniz?");

		Scanner scanner = new Scanner(System.in);
		String data = "";
		while (true) {
			System.out.println("Çıkmak için : exit (z) ye basar mısınız? veya entere basınız? ");
			data = scanner.nextLine();
			System.out.print("İşlemi girin : ");
			denklem = scanner.nextLine();
			
			if (data.equals("z")||denklem.equals("z")) {
				System.out.println("Hesap makinesi kapatılıyor. iyi günler!");
				break;
			}

		

			denklem = denklem.replaceAll(" ", "");
			System.out.print("sonuc = ");
			try {
				System.out.println(Calculate(denklem));
			} catch (Error e) {
				System.out.println(e);

			}

		}
	}

}