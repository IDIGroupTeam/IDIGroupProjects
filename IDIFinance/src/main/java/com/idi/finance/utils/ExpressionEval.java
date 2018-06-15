package com.idi.finance.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.apache.log4j.Logger;

public class ExpressionEval {
	private static final Logger logger = Logger.getLogger(ExpressionEval.class);

	public static void main(String[] args) {
		ExpressionEval expEval = new ExpressionEval();
		// String inFixExp = "A*B+C*((D-E)+F)/G";
		String inFixExp = "10*2+4*((11-2)+9)/5";

		logger.info("Kết quả biểu thức: " + expEval.calExp(inFixExp));
	}

	private static int priority(String opt) {
		if (opt.matches("[*/%]")) {
			return 2;
		} else if (opt.matches("[+-]")) {
			return 1;
		}
		return 0;
	}

	public static boolean isOperator(String operator) {
		return operator.matches("[-+*/%]");
	}

	private static boolean isOpenBounder(String operator) {
		return operator.matches("[(\\[{]");
	}

	private static boolean isCloseBounder(String operator) {
		return operator.matches("[)\\]}]");
	}

	public static String formatExpression(String expression) {
		if (expression != null) {
			String[] operators = { "+", "-", "*", "/", "%", "(", ")", "[", "]", "{", "}" };
			for (int i = 0; i < operators.length; i++) {
				expression = expression.replace(operators[i], " " + operators[i] + " ");
			}

			expression = expression.replaceAll("\\s+", " ");
			expression = expression.trim();
		}
		return expression;
	}

	/**
	 * Chuyển biểu thức trung tố thành hậu tố. Việc chuyển biểu thức trung tố thành
	 * biểu thức tiền đố tương tự.
	 * 
	 * @param infix
	 *            expression
	 * @return postfix expression
	 */
	private static String inFix2PostFix(String expression) {
		// Chuẩn hóa biểu thức trung tố
		expression = formatExpression(expression);
		// logger.info("Biểu thức trung tố: " + expression);

		// Chuyển biểu thức trung tố thành hậu tố.
		if (expression != null) {
			// Tách biểu thức trung tố
			String[] exps = expression.split(" ");

			StringBuffer output = new StringBuffer();
			Stack<String> stack = new Stack<>();

			// Bắt đầu chuyển đổi
			for (int i = 0; i < exps.length; i++) {
				// System.out.print(exps[i] + "\t");
				if (isOpenBounder(exps[i])) {
					// Gặp các dấu ngoặc mở thì đưa nó vào stack
					stack.push(exps[i]);
				} else if (isCloseBounder(exps[i])) {
					// Gặp các dấu ngoặc đóng thì đưa toàn bộ toán tử ra output
					// từ stack ra đến khi gặp dấu ngoặc mở đầu tiên
					while (!isOpenBounder(stack.peek())) {
						output.append(stack.pop() + " ");
					}

					// Xóa thẻ đóng khỏi stack
					stack.pop();
				} else if (isOperator(exps[i])) {
					if (stack.empty() || (priority(exps[i]) > priority(stack.peek()))) {
						// Nếu lần đầu tiên gặp toán tử
						// hoặc toán tử hiện tại có độ ưu tiên không lớn hơn toán tử ở đầu stack
						// thì đẩy toán tử hiện tại lưu vào stack
						stack.push(exps[i]);
					} else {
						// Nếu toán từ hiện tại có độ ưu tiên thấp hơn toán từ ở đầu stack
						// thì lấy toán tự ở đầu stack đưa ra output và
						// đẩy toán tử hiện tại vào stack
						output.append(stack.pop() + " ");
						stack.push(exps[i]);
					}
				} else {
					// Nếu là toán hạng thì đưa ra output
					output.append(exps[i] + " ");
				}
				// logger.info(output);
				// logger.info(" \t" + stack);
			}

			// Lấy toàn bộ toán từ còn lại trong stack đưa ra output
			while (!stack.empty()) {
				output.append(stack.pop() + " ");
			}

			return formatExpression(output.toString());
		}
		return null;
	}

	/**
	 * Tính giá trị biểu thức hậu tố
	 * 
	 * @param postFixExp
	 * @return
	 */
	private static double calPostFixExp(String postFixExp) {
		double value = 0;

		// Chuẩn hóa biểu thức hậu tố
		postFixExp = formatExpression(postFixExp);
		// logger.info("Biểu thức hậu tố: " + postFixExp);

		// Tính giá trị biểu thức hậu tố.
		if (postFixExp != null) {
			// Tách biểu thức hậu tố
			String[] exps = postFixExp.split(" ");

			// Bắt đầu tính giá trị
			Stack<Double> stack = new Stack<>();
			Double tmp = 0.0;

			for (int i = 0; i < exps.length; i++) {
				if (isOperator(exps[i])) {
					// Nếu là toán từ thì lấy 2 toán hạng ở đầu stack ra tính với toán từ đó
					// Kết quả đẩy ngược lại stack
					Double value1 = stack.pop();
					Double value2 = stack.pop();

					switch (exps[i]) {
					case "+":
						tmp = value2 + value1;
						break;
					case "-":
						tmp = value2 - value1;
						break;
					case "*":
						tmp = value2 * value1;
						break;
					case "/":
						if (value1 != 0) {
							tmp = value2 / value1;
						} else {
							return 0;
						}

						break;
					case "%":
						tmp = value2 % value1;
						break;
					default:
						break;
					}

					stack.push(tmp);
				} else {
					// Nếu là toán hạng thì đẩy vào stack, sau này lôi ra tính toán sau
					try {
						Double operandValue = new Double(exps[i]);
						stack.push(operandValue);
					} catch (Exception e) {
						// Vì một lý do nào đó dữ liệu bị thiếu thì coi như bằng 0.0
						stack.push(new Double(0));
					}
				}
			}

			// Lấy kết quả cuối cùng
			value = stack.pop();
		}

		return value;
	}

	/**
	 * Tính giá trị biểu thức
	 * 
	 * @param postFixExp
	 * @return
	 */
	public static double calExp(String expression) {
		// Chuyển sang biểu thức hậu tố
		String postFixExp = inFix2PostFix(expression);

		// Tính giá trị biểu thức hậu tố và trả về kết quả
		return calPostFixExp(postFixExp);
	}

	/**
	 * Lấy danh sách toán từ của biểu thức
	 * 
	 * @param expression
	 * @return
	 */
	public static List<String> getOperands(String expression) {
		if (expression == null)
			return null;

		List<String> rs = new ArrayList<>();

		expression = formatExpression(expression);
		String[] splits = expression.split(" ");
		for (int i = 0; i < splits.length; i++) {
			if (!isOperator(splits[i]) && !isCloseBounder(splits[i]) && !isOpenBounder(splits[i])) {
				rs.add(splits[i].trim());
			}
		}

		return rs;
	}
}
