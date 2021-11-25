package compiladores;

import java.util.Stack;


import java.util.LinkedList;
import java.util.Scanner;

public class Escaneador {
	@SuppressWarnings("unchecked")
	public static void main (String args[]) {
		LinkedList<Token> tokenList= new LinkedList<Token>();
		Scanner in = new Scanner(System.in);
		String entry = "";
		while (in.hasNext()) {
			String a = in.next();
			if (!a.contentEquals("break"))
				entry = entry + "@" + a;
			else
				break;
		}in.close();
		Tuple errorAux =  Scan(entry);
		if ((boolean) errorAux.y == false) {
			tokenList = (LinkedList<Token>) errorAux.x;
			Integer answ = RPN(tokenList);
			System.out.println(answ);
		}
	}
	//Método utilizado para realizar a execução do que está guardado na lista de símbolos (tokens)
	
	public static Integer RPN(LinkedList<Token> list) {
		Stack<Integer> stack = new Stack<Integer>();
		Token currentToken = null;
		for (int i = 0; i<list.size(); i++) {
			currentToken = list.get(i);
			if (currentToken.type == TokenType.NUM) 
				stack.push(Integer.parseInt(currentToken.lexeme));
			else if (currentToken.type == TokenType.EOF) {
				if (stack.size()>1) {
					System.out.println("Error: Reached EOF while parsing");
					return -2;
				}
				return stack.pop();
			}
			else {
				Integer x = stack.pop();
				Integer y = stack.pop();
				if (currentToken.type == TokenType.MINUS) 
					stack.push(y-x);
				if (currentToken.type == TokenType.STAR)
					stack.push(x*y);
				if (currentToken.type == TokenType.SLASH)
					stack.push(y/x);
				if (currentToken.type == TokenType.PLUS)
					stack.push(x+y);
			}
		}
		System.out.println("Error: Malformed expression");
		return -1;
	}
	//Método utilizado para escanear a entrada
	public static Tuple<LinkedList<Token>, Boolean> Scan(String entry) {
		LinkedList<Token> tokenList = new LinkedList<Token>();
		Token currentToken = null;
		TokenType currentTokenType = null;
		Boolean error = false;
		entry = entry.substring(1);
		String[] entryList = entry.split("@"); 
		for (int i = 0; i<entryList.length; i++) {
			if (isNum(entryList[i])){
				currentTokenType = TokenType.NUM;
			}else if (isOp(entryList[i])) {
				currentTokenType = getTokenType(entryList[i]);
			}else {
				System.out.println("Error: Unexpected character	:" + entryList[i]);
				error = true;
				break;
			}
			currentToken = new Token(currentTokenType, entryList[i]);
			tokenList.add(currentToken);
		}tokenList.add(new Token (TokenType.EOF, ""));
		return new Tuple<LinkedList<Token>, Boolean>(tokenList, error) ;
			
	}
	public static boolean isOp (String entry) {
		if (entry.length()>1)
			return false;
		char op = entry.charAt(0);
		if (op == '*' | op == '%' || op == '+' || op == '-')
			return true;
		return false;
	}
	//Used to get operation token type
	public static TokenType getTokenType(String entry){
		TokenType type = null;
		char op = entry.charAt(0);
		switch (op) {
		case '*':
			type = TokenType.STAR;
			break;
		case '%':
			type = TokenType.SLASH;
			break;
		case '+':
			type = TokenType.PLUS;
			break;
		case '-':
			type = TokenType.MINUS;
			break;
		}
		return type;
	}
	public static boolean isNum(String entry) {
		try {
			Integer.parseInt(entry);
			return true;
		}catch (NumberFormatException e) {
			
		}return false;
	}
	
	
	public enum TokenType { 

		// Literals.
		NUM,

		// Single-character tokens for operations.
		MINUS, PLUS, SLASH, STAR,
		
		EOF

	}
	public static class Token {

		public final TokenType type; // token type
		public final String lexeme; // token value

		public Token (TokenType type, String value) {
			this.type = type;
			this.lexeme = value;
		}

		@Override
		public String toString() {
			return "Token [type=" + this.type + ", lexeme=" + this.lexeme + "]";
		}
	}
	public static class Tuple<X, Y> { 
		  public final X x; 
		  public final Y y; 
		  public Tuple(X x, Y y) { 
		    this.x = x; 
		    this.y = y; 
		  } 
		} 
}

