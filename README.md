
# 💸 SwiftPay - Android Payment App (Prototype)

SwiftPay is a **prototype payment application** built with **Kotlin** and **Jetpack Compose**, designed for educational and demonstration purposes. It replicates the core UI/UX features of modern digital payment apps like Google Pay or Paytm — but **without any real payment processing**.

---

## 📲 What is SwiftPay?

SwiftPay is a mock UPI-style app that simulates digital wallet interactions such as:
- Sending money (fake UPI ID or mobile number)
- Checking wallet balance
- Scanning QR codes (UI only)
- Paying bills (mock buttons)
- Logging in via OTP (simulated authentication)

This project is built as a **Skill Lab submission** and is ideal for learning how to build a complete modern Android application with UI components, navigation, and Firebase integration (mocked).

---

## 📸 App Screenshots


![Screenshot 2025-06-10 113824](https://github.com/user-attachments/assets/dfd4d10c-2c9a-4043-899f-8877b740e238)

---

## ✨ Features

### 👤 User Authentication (Simulated)
- Enter phone number and verify OTP (Firebase UI logic only, not secure auth)

### 💼 Wallet & Balance
- Displays mock wallet balance
- Each user has a dummy UPI ID (e.g. `alice@swiftpay`)

### 💰 Send Money (Prototype)
- Enter UPI ID or mobile number and amount to simulate money transfer
- Updates Firebase document to reflect mock transaction

### 🔍 Services Grid
- Quick access icons for Wallet, Pay Bills, Recharge, etc.

### 📄 Bills & Recharges
- Electricity, Water, Gas, Broadband buttons (UI only)

### 🧮 Financial Tools
- Loan EMI Calculator (UI only)
- Budget Planner
- Credit Score Checker (no real API integration)

### 🎁 Rewards & Referrals
- Simulates "Invite & Earn ₹100 cashback"

---

## ⚙️ How it Works

Although **no actual UPI or payment gateway is integrated**, SwiftPay simulates app-like behavior using:

| Functionality      | How It Works                         |
|--------------------|---------------------------------------|
| Authentication     | Firebase Auth with phone (UI only)    |
| Wallet Balance     | Stored in Firebase Firestore          |
| Transactions       | Dummy entries in Firestore            |
| Navigation         | Jetpack Compose Navigation            |
| UI Components      | Material 3, Custom Composables        |

---

## 🏗️ Tech Stack

| Component        | Technology Used                |
|------------------|--------------------------------|
| Language         | Kotlin                         |
| UI Framework     | Jetpack Compose                |
| Architecture     | MVVM (basic)                   |
| Backend (Mock)   | Firebase Firestore             |
| Tools            | Android Studio, Gradle         |

---

## 🚀 Getting Started

### Prerequisites

- Android Studio (Flamingo or later)
- Kotlin 1.9+
- Firebase Console Project (optional)

### Setup

1. Clone the repo:
    ```bash
    git clone https://github.com/Chinmaysahoo03/PaymentApp.git
    cd PaymentApp
    ```
2. Open in Android Studio
3. Sync Gradle files
4. (Optional) Add `google-services.json` from your Firebase project
5. Run on Emulator or Device

---

## 🗂️ Folder Structure

```
PaymentApp/
├── app/
│   ├── src/main/java/com/example/paymentapp/
│   │   ├── ui/                  # UI Composables and Screens
│   │   ├── data/                # Firebase-related logic
│   │   └── MainActivity.kt
│   └── res/                     # Resources (icons, colors, themes)
├── build.gradle
└── README.md
```

---

## ⚠️ Disclaimer

> This project is a **prototype** for educational and UI/UX purposes only.  
> No real money is handled. No external banking APIs are used.  
> It is **not secure** for real financial operations.

---

## 👨‍💻 Author

Built with ❤️ by **Chinmay Sahoo**  
📍 Makerspace Skill Lab Project  
🔗 [GitHub Profile](https://github.com/Chinmaysahoo03)

---

## 📄 License

Licensed under the [MIT License](LICENSE)
