# MyData

MyData is a note management platform that helps users create, organize, and manage notes by categories and tags. It also features AI-powered summarization, robust authentication, session handling, and detailed action logging.

---

## 📌 Summary

- Notes can be **created and organized** by category and tagged for better sorting and retrieval.
- Notes are **editable**, allowing content, category, and tags to be updated.
- Categories are **automatically created or removed** based on note presence.
- **BART AI model** (from Hugging Face) is used to **summarize notes** during creation.
- **User authentication and session management** is implemented for secure usage.
- Sessions are tracked and maintained using DB timestamps, allowing:
  - Multiple open tabs without repeated login.
  - Automatic logout on inactivity.
- All backend actions are **logged** for audit and debugging.

---

## ✨ Functionality

- ➕ **Create Note**: Click the `+` button to open a new note window.
- 🧠 **Summarize**: Click the `Summarize` button to generate a summary using the BART AI model.
- 🏷️ **Tagging**:
  - Use the `Add Tag` option to add tags (press Enter to confirm).
  - Remove tags via the `x` button next to them.
- 🗂️ **Categorization**:
  - Use the dropdown to assign notes to categories.
  - Create a new category from the dropdown if needed.
- 🧱 **Note Tiles**:
  - Notes appear as tiles in their categories.
  - Click to open and **edit title, tags, category, and content**.
- ♻️ **Update/Delete**:
  - Update any note details and click `Update` to save.
  - Use `Delete` to remove a note.
- 🔀 **Move Notes**: Changing the category dropdown moves the note.
- 🔎 **Filter by Tags**:
  - Use the tag filter at the top to see notes with selected tags within a category.

---

## 🔐 Authentication & Session Handling

- 📝 **Sign Up**:
  - Users sign up with name, email, and password.
  - Data is stored in the `TD_USER_INFO` table.
  - A unique `UID` is generated for each user.

- 🔑 **Login**:
  - Verifies email and password.
  - Generates and returns a unique `Session ID` and `UID`.

- ⏱️ **Session Expiry**:
  - Login time is recorded to calculate session expiry.
  - Expiry duration is configurable via `MD_APPLICATION_PARAMETERS`.
  - Any authenticated API action **extends session expiry**.
  - All non-authentication requests include `UID` and `Session ID` in headers.
  - Expired sessions result in a `TIMEOUT` response and a login prompt.

- 🚪 **Logout**:
  - Manually ends the session.

---

## 📝 Logging

All backend actions are logged for tracking:

- **State**: `SUCCESS`, `FAILED`, `TIMEOUT`, etc.
- **Action**: Indicates the backend endpoint accessed.
- **Request/Response**: Logged data exchanged with the frontend.
- **Time**: Timestamp of the server's response.

---

## 📦 Tech Stack

- Frontend: React
- Backend: Java
- AI: Hugging Face's BART model
- Database: (assumed Oracle DB)
- Authentication: Session-based with DB tracking

---

## 🚀 Future Improvements

- UI enhancements
- Role-based access control
- Search across categories

---

## 📄 License

This project is licensed under the MIT License.
