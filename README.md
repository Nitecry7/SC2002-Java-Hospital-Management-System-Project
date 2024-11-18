# SC2002 Java Hospital Management System Project 🏥
A Command Line Interface based Hospital Management System that simulates almost all processes that occur at a real Hospital 💊🌡️🩹💉🩺

## Project Folder Structure 📂

│ <br>
├ src: All the code are stored in here.<br>
├ doc: The javadoc for our codes.<br>
├ LabInstruction: Files given as the assignment releases.<br>
└ hmsystem: Our launching script<br>

## How To Run Our Project? 🚀

**Only one simple command!**

```shell
./hmsystem
```

## Overview of Project 🔎

### Entities ⚙️

#### Appointments 🗓️

The appointment between doctors and patients.<br> 
Both doctor and patient can leave notes for more efficient communication.<br>

#### Inventory 💼

All the medicine are stored here.<br>
When the amount is low, it would show a message to alert user.<br>

#### Replenishment 💊

You can create a replenishment request.<br>
System will ask if you want to request the medication under alert line.<br>
Submit to admins, they either approve or reject it.<br>

### Roles 📌
#### Patient 🛌
- Able to schedule/reschedule/cancel appointments with doctors
- View their own medical record and appointment results
- Update their info
#### Doctor 🩺
- Able to view their appointments 
- View their patient's past medical records
- Set availability so that you can have your own times
- Prescribe medications
#### Pharmacist ℞☤💊🥼
- Dispense medication prescriptions
- Submit replenishment requests
#### Administrator 🛡️
- Can view and manage all staff/inventory
- Also able to approve replenishments
#### All Users 👥
- Able to change password anytime they want

## Upcoming Features ✨

In the future, we wish to improve it by
- Adding hashing passwords to be more secured
- Allow Staff to manage their personal info
- Make more user friendly UI

## UML Diagram

Brief Outline illustrated by the UML Diagram 📊
![OOP Updated UML Diagram (As of 18 Nov 2024)](https://github.com/Nitecry7/SC2002-Java-Hospital-Management-System-Project/blob/main/UML-Class-Diagram.png?raw=true)

## All Contributors w/ contributions 👨‍💻
* Faheem (Nitecry7) - Javadoc
* Mario (PresidentDoggo) - Controllers
* Juber (AstraytLUL) - Models
* Qing Rong (CobaltConcrete) - IO Handlers
* Lex (lpqtan) - UML Diagram
