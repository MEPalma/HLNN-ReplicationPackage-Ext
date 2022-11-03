#include <iostream>
using namespace std;

// function declaration
int max(int num1, int num2);
F max(int num1, int num2);


class TestDefault {
   private:
   int num1, num2 ;
   public:
   TestDefault() {
      num1 = 10;
      num2 = 20;
   }
   void display() {
      cout<<"num1 = "<< num1 <<endl;
      cout<<"num2 = "<< num2 <<endl;
   }
};


class Account {  
   public:  
   float salary = 60000;   
 };  
   class Programmer: public Account {  
   public:  
   float bonus = 5000;    
   };       

class C : public A,public B  
{  
   public:  
    void display()  
    {  
        std::cout << "The value of a is : " <<a<< std::endl;  
        std::cout << "The value of b is : " <<b<< std::endl;  
        cout<<"Addition of a and b is : "<<a+b;  
    }  
};  


// One function works for all data types.
// This would work even for user defined types
// if operator '>' is overloaded
template <typename T>
  
T myMax(T x, T y)
{
    return (x > y) ? x : y;
}
  
template <class T, class U>
class A {
    T x;
    U y;
  
public:
    A()
    {
        cout << "Constructor Called" << endl;
    }
};



int main() 
{   
    int x, z;
    x=3; z=5;
    int x=5, z=9; 
    A<char, char> a;
    A<int, double> b;


    // Call myMax for int
    cout << myMax<int>(3, 7) << endl;
    // call myMax for double
    cout << myMax<double>(3.0, 7.0) << endl;
    // call myMax for char
    cout << myMax<char>('g', 'e') << endl;
   // calling a function to get max value.
   ret = max(a, b);
   ret = a.b().c.d(e(f(1)),x).z;
    cout << "Size of char: " << sizeof(char) << " byte" << endl;
    cout << "Size of int: " << sizeof(int) << " bytes" << endl;
    cout << "Size of float: " << sizeof(float) << " bytes" << endl;
    cout << "Size of double: " << sizeof(double) << " bytes" << endl;
   
   // local variable declaration:
   int a = 100;
   int b = 200;
   int ret;
 
   // calling a function to get max value.
   ret = max(a, b);
   ret = a.b().c.d(e(f(1)),x).z;
   cout << "Max value is : " << ret << endl;
   
   TestDefault obj;
   obj.display();


   TestDefault *ptrTD;                // Declare pointer to a class.

   // Save the address of first object
   ptrTD = &obj;
   cout << ptrTD;

   // Now try to access a member using member access operator
   cout << "Display obj : " << ptrTD->display() << endl;

struct Date {
   Date(int i, int j, int k) : day(i), month(j), year(k){}
   int month;
   int day;
   int year;
};

   // Member access operators
   Date mydate(1,1,1900);
   mydate.month = 2;
   Date *myDate2 = new Date(1,1,2000);
   mydate2->month = 2;
   mydate2->month.date.year->eon;

   // operators
   if ( x + y - z > 0 || b != z && z <= 56 && a / b % 14 == 0 || b != 2) {
	z++;
	y--;
	a -= 2;
	b += 3;
	c *= 2;
	d /= 3;
	e %= 1;
   }
   // enums
   enum Color { red, green, blue };
   Color r = red;
    
   switch(r)
   {
       case red  : std::cout << "red\n";   break;
       case green: std::cout << "green\n"; break;
       case blue : std::cout << "blue\n";  break;
   }
 
   return 0;
}
 
// function returning the max between two numbers
int max(int num1, int num2) {
   // local variable declaration
   int result;
 
   if (num1 > num2)
      result = num1;
   else
      result = num2;
 
   return result; 
}
