📱 MrChat – Real‑Time Chat Application
MrChat is a Firebase‑backed Android chat application built with Java/Kotlin and Material Design. It enables users to register, log in, view contacts, and exchange messages in real time with a clean, modern UI.

✨ Features
🔐 User Authentication – Register and log in securely with Firebase Authentication.

👥 Contact List – Displays all registered users except the logged‑in one.

💬 One‑to‑One Chat – Tap a contact to open a private chat window.

🕒 Timestamps – Each message shows when it was sent.

🎨 Material Design UI – Modern card‑based layouts for header, message list, and input bar.

🚪 Logout – Sign out easily with one tap.

📂 Project Structure
AuthenticationActivity → Handles login and registration.

MainActivity → Shows contact list and logged‑in user info.

ChatActivity → Displays chat messages and input field.

MessageAdapter → Renders messages with timestamps and alignment.

ContactAdapter → Displays contacts with avatar and username.

Firebase Realtime Database → Stores users and chat messages.

🛠️ Setup Instructions
Clone the repository

bash
git clone https://github.com/aayushrajthakur/MrChat.git
cd MrChat
Open in Android Studio

File → Open → Select the MrChat project folder.

Configure Firebase

Create a Firebase project at Firebase Console (console.firebase.google.com in Bing).

Enable Authentication (Email/Password).

Enable Realtime Database.

Download google-services.json and place it in app/ directory.

Build & Run

Sync Gradle.

Run on emulator or physical device.

🚀 How to Use
Register – Enter username, email, and password to create an account.

Login – Use your credentials to access the app.

View Contacts – See all other registered users in the list.

Start Chat – Tap a contact to open a chat window.

Send Messages – Type a message and press Send.

See Timestamps – Each message shows when it was sent.

Logout – Tap the logout button to end your session.

🔒 Security Notes
Secrets (API keys, keystore files, etc.) are excluded via .gitignore.

Firebase rules should restrict read/write access to authenticated users only.
