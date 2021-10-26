package Rascunho;

import java.util.Stack;
import java.util.Scanner;

public class ReversePolishNotation {
	public static void main (String args[]) {
		Stack<Integer> pilha= new Stack<Integer>();
		//Na implementação consideraremos -1 = - -2 = + -3 = % -4 = * na pilha
		Scanner in = new Scanner(System.in);
		while (in.hasNext()) {
			int a = -1;
			if (in.hasNextInt()) {
				a = in.nextInt();
				if(a == -5)
					break;
			}
			int b = -1;
			if (in.hasNextInt()) {
				b = in.nextInt();
			}
			//Empilhamento dos números
			if (a!=-1) {
				pilha.push(a);
			}
			if (b!=-1) {
				pilha.push(b);
			}
			char op = in.next().charAt(0);
			//Número que representa a operação a ser armzenada na pilha
			Integer x = pilha.pop();
			Integer y = pilha.pop();
			switch (op) {
				case '*':
					pilha.push(x*y);
					break;
				case '%':
					pilha.push(y/x);
					break;
				case '+':
					pilha.push(x+y);
					break;
				case '-':
					pilha.push(y-x);
					break;
			}
		}System.out.println(pilha.pop());
		
			
	}
}

