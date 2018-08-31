/**************************************************************************************************************************/
/*                                          OOPS ASSIGNMENT - CALGO - JAVA                                                */
/*                      RISHI KESAV MOHAN                                        2017103031                               */                              
/*                                                                                                                        */
/*                              Note: The following is a part of an Android App - CALGO                                   */ 
/**************************************************************************************************************************/

package com.example.mohan.calgo;                //PACKAGING

import android.os.Bundle;                       //IMPORTING NECESSARY COMPONENTS
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.text.TextWatcher;
import android.text.Editable;
import static java.lang.StrictMath.pow;


public class MainActivity extends AppCompatActivity {   //INHERITANCE
  EditText et1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1=findViewById(R.id.editText2) ;
        et1.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                postfixify(et1);
            }
        });

    }//initialises the view and calls postfixify whenever there is change in input

    public int length(char[] infix){
        int length=1;
        int i;
        int prev,cur;
        if(Character.isDigit(infix[0]))
            prev=0;
        else
            prev=1;

        for(i=1;i<infix.length;i++) {
            if (Character.isDigit(infix[i])) {
                cur = 0;
                if (prev == 1) {
                    prev=cur;
                    length++;
                }
            } else {
                cur=1;
                if(prev==0) {
                    prev=cur;
                    length++;
                }
            }
        }
        return length;
    }//returns the element(tot of operands nd operators) length of the exp

    public boolean infixtrue(char[] infix){
        int i=0;
        if(infix.length<3)
            return false;
        if(length(infix)<3)
            return false;
        while(i<infix.length-1){
            if(!Character.isDigit(infix[i])&&Character.isDigit(infix[i+1])&&Character.isDigit(infix[i-1])){
                return true;
            }
            i++;
        }
        return false;
    }//checks the correctness of the exp

    public void postfixify(View view){

       Stack operators = new Stack();
            String postfix = new String();
            String infix = et1.getText().toString();
            char[] c=infix.toCharArray();
           if(infixtrue(c)) {
                for (int i = 0; i < infix.length(); ++i) {
                    if(Character.isDigit(c[i])) {
                        String num=new String();
                        num = num + Character.toString(c[i]);
                        postfix = postfix + num ;
                        if(i==0&&Character.isDigit(c[i])&&!Character.isDigit(c[i+1])) {
                            postfix = postfix + '|';
                        }
                        else if((i+1<c.length&&!Character.isDigit(c[i+1])&&i-1>-1&&Character.isDigit(c[i-1]))||((i+1<c.length&&!Character.isDigit(c[i+1])&&Character.isDigit(c[i])&&i-1>-1&&!Character.isDigit(c[i-1])))){
                            postfix = postfix + '|';
                        }
                        else if(i==c.length-1&&Character.isDigit(c[i])){
                            postfix = postfix + '|';
                        }
                    }
                    else if (c[i] == '(') {

                        operators.push(c[i]);
                    } else if (c[i] == ')') {
                        while (operators.peek() != '(') {
                            postfix = postfix + operators.pop();
                        }
                        operators.pop();
                    } else {
                        while (!operators.isEmpty() && !(operators.peek() == '(') && prec(c[i]) <= prec(operators.peek()))
                            postfix = postfix + operators.pop();

                        operators.push(c[i]);
                    }
                }
                while (!operators.isEmpty())
                    postfix = postfix + operators.pop();
              // TextView t2 = findViewById(R.id.textView2);
              // t2.setText(postfix);
               postfixeval(postfix);
            }
            else{
                TextView t2 = findViewById(R.id.textView2);
                t2.setText(infix);
            }
    }//converts infix to postfix

    static int prec(char x)//operator precedence
    {
        if (x == '+' || x == '-')
            return 1;
        if (x == '*' || x == '/' || x == '%'|| x=='^')
            return 2;
        return 0;
    }

    public void postfixeval(String postfi){

        TextView t2 = findViewById(R.id.textView2);
        IntStack s = new IntStack();
        char[] chars = postfi.toCharArray();
        int N = chars.length;
        for (int i = 0; i < N; i++) {
            char ch = chars[i];
            if (ch=='+'||ch=='-'||ch=='*'||ch=='/'||ch=='^'||ch=='%') {
                double x=s.pop(),y=s.pop();
                switch (ch) {
                    case '+': s.push(x + y);     break;
                    case '*': s.push(x * y);     break;
                    case '-': s.push(-x + y);    break;
                    case '/': s.push(1 / x * y); break;
                    case '%': s.push((int)y % (int)x); break;
                    case '^': s.push(pow(y,x)); break;
                    }
            }else if(Character.isDigit(ch)) {
                int num=0;
                while(i<N&&chars[i]!='|'){
                    num=num*10+(chars[i]-'0');
                    i++;
                }
                s.push((double)num);
            }
        }
        Double res;
        if (!s.isEmpty())
            res = s.pop();
        else
            res =  0.0;
        String op = Double.toString(res);
        t2.setText(op);
        System.gc();        //GARBAGE COLLECTION
    }//evaluates nd displays postfix exp

    public void zero(View view){
        String temp=et1.getText().toString();
        et1.setText(temp+"0"); }
    public void one(View view){
        String temp=et1.getText().toString();
        et1.setText(temp+"1");
    }                                                       //BUTTON RESPONSES
    public void two(View view){
        String temp=et1.getText().toString();
        et1.setText(temp+"2");
    }
    public void three(View view){
        String temp=et1.getText().toString();
        et1.setText(temp+"3");
    }

    public void four(View view){
        String temp=et1.getText().toString();
        et1.setText(temp+"4");
    }
    public void five(View view){
        String temp=et1.getText().toString();
        et1.setText(temp+"5");
    }
    public void six(View view){
        String temp=et1.getText().toString();
        et1.setText(temp+"6");
    }
    public void seven(View view){
        String temp=et1.getText().toString();
        et1.setText(temp+"7");
    }
    public void eight(View view){
        String temp=et1.getText().toString();
        et1.setText(temp+"8");
    }
    public void nine(View view){
        String temp=et1.getText().toString();
        et1.setText(temp+"9");
    }
    public void plus(View view){
        String temp=et1.getText().toString();
        et1.setText(temp+"+");
    }
    public void sub(View view){
        String temp=et1.getText().toString();
        et1.setText(temp+"-");
    }
    public void multi(View view){
        String temp=et1.getText().toString();
        et1.setText(temp+"*");
    }
    public void div(View view){
        String temp=et1.getText().toString();
        et1.setText(temp+"/");
    }
    public void modulo(View view){
        String temp=et1.getText().toString();
        et1.setText(temp+"%");
    }
    public void expo(View view){
        String temp=et1.getText().toString();
        et1.setText(temp+"^");
    }

    public void del(View view) {
        String temp = et1.getText().toString();
        if (temp.length() > 0) {
            et1.setText(temp.substring(0, temp.length() - 1));
        }
    }
}
interface sta   //INTERFACE
{
    public void push(char c);
    public char pop();
    public boolean isEmpty();
}

class Stack implements sta //for postfixify  //INTERFACE IMPLEMENTATION
{
    char a[]=new char[100];
    int top=-1;

    public void push(char c)
    {
        if(!(top==99)) {
            this.a[++top] = c;
        }
    }

    public char pop()
    {
        if(top!=-1)
            return this.a[top--];
        else
            return 0;
    }

    public boolean isEmpty()
    {
        return (top==-1)?true:false;
    }

    public char peek()
    {
        return this.a[top];
    }

};
class IntStack //for postfixeval
{
    double a[]=new double[100];
    int top=-1;

    void push(double c)
    {
        if(!(top==99)) {
            this.a[++top] = c;
        }
    }

    double pop()
    {
        if(top!=-1)
            return this.a[top--];
        else
            return 0;
    }

    boolean isEmpty()
    {
        return (top==-1)?true:false;
    }

    double peek()
    {
        return this.a[top];
    }

};
