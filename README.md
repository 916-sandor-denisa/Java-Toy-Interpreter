# **Toy Language Interpreter**

A custom "toy" language interpreter built in Java, designed to demonstrate core programming concepts and techniques.

---

## **Features**

### **Used Concepts**
- **Layered architecture**
- **JavaFX** for GUI development
- **Encapsulation**, **interfaces**, and **streams**
- **MVC pattern** for clear separation of concerns

### **Supported Instructions**
- **Logical expressions**
- **Arithmetical expressions**
- **Relational expressions**
- **Variable declaration & assignment**
- **Printing**
- **If statements**
- **While loops**
- **File operations**: opening, closing, and reading files
- **Fork (multi-threading)**
- **Heap operations**: allocation, reading, and writing

### **Variable Types**
- **Bool**
- **Int**
- **String**
- **Reference**

---

## **Functionalities**

- **Execution Stacks**: Instructions are stored and executed from stacks.
- **Symbol Tables**: Local variables are stored in symbol tables.
- **File Table**: Stores `BufferedReader` objects used for file operations.
- **Shared Heap**: A shared heap across all program states for allocation, reading, writing, and garbage collection.
- **Output Table**: Stores output to be printed during program execution.
- **Program States**:
  - Each program state has a **unique ID**, a **symbol table**, and an **execution stack**.
  - **Forking** creates a new program state with a unique ID.

---

## **Demo**
![image](https://github.com/user-attachments/assets/2a3247df-254b-4e77-989f-a555b9fa4315)

