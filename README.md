# MyData

**MyData** is a backend service designed to manage and organize user-specific tile data, including titles, categories, and tags.  
It stores structured information efficiently while handling tags as a list internally and a `|`-separated string in the database.

Built with **Java**, **Spring Boot**, **Hibernate (JPA)**, and **MySQL/Oracle DB**.

---

## ✨ Features

- Create, update, and retrieve tile data.
- Tags managed as `List<String>` in Java, stored as `String` in DB.
- Full CRUD operations on tiles.
- Modular and expandable codebase.

---

## 🏗️ Tech Stack

- Java 17+
- Spring Boot
- Spring Data JPA (Hibernate)
- Postgre Database
- Lombok

---

## 📦 Project Structure

```bash
src/main/java/com/MyData
├── Controller/          # API endpoints (Coming Soon)
├── Dao/                 # JPA Entities (TileDataDao)
├── Converter/           # Custom converters (TagsConverter)
└── Repository/          # Database access layer (TileDataRepository)
