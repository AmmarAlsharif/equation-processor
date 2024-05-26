# equation-processor

This project is like a simple calculator app, it doesn't solve any kind of mathematical equations, it just supports the basic calculator operations which are `+ - / *` and the exponentiation (power) operator `^`.

# Run
Start the application like any usual java app, then you can write your input in the console.

#### Examples:
**Input:**  
``5 * 5 + (2.2 + 1 + 0.8)^2``  
**Output:**  
``41.0``  

**Input:**  
``( -2 ^-(12.3 + 1 - 1) ^ ( (1 + (2.2 - 1)) / 12.3) )``  
**Output:**  
``-0.3376163771132725``  

**Input:**  
You can use variables then provide their values.  
**Note:** Variables are case-insensitive.  
``x  * X + ( ( 2 . 2 +   0 . 8 )   *   2 ) ^ y ^ z``  
**Output:**
```
Enter the value of "x"
10
Enter the value of "y"
2
Enter the value of "z"
2
1396.0
```