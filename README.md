# 🎬 MyMovieApp

**MyMovieApp** is a **full-stack Spring Boot CRUD application** for managing movies with a secure backend and a dynamic frontend. It integrates **Spring Boot, MySQL, Thymeleaf, Bootstrap, and Cloudinary** to provide a modern, production-ready solution that follows the **MVC architecture**.

---

## ⚡ Technology Stack

* **Backend**: Spring Boot (Spring Data JPA, Spring Security, MVC, Scheduler, Interceptors)
* **Frontend**: Thymeleaf, HTML, CSS, Bootstrap, JavaScript, jQuery (AJAX API calls)
* **Database**: MySQL (local)
* **File Storage**: Cloudinary
* **Email Service**: MailHog (via Docker)
* **Testing**: Unit and integration tests (JUnit, Spring Test)
* **Security**: Role-based access control (`USER`, `ADMIN`, `ROOT`), custom validation, interceptors
* **Extras**: Custom validation, scheduled jobs, hidden development easter egg 🍪

---

## ✨ Key Features

* 🔑 **CRUD Operations**: Create, Read, Update, and Delete movies with a simple and intuitive interface.
* 🔒 **Authentication & Authorization**: Secure login and registration with Spring Security and roles (`USER`, `ADMIN`, `ROOT`).
* 📸 **Image Management**: Upload, store, and serve images seamlessly with Cloudinary.
* 📧 **Email Notifications**: Outgoing emails handled via **MailHog** (SMTP through Docker), ideal for local testing.
* 💾 **Database Integration**: Local MySQL with auto schema updates and initialization.
* ✅ **Form Validation**: Strong custom validation for user input to ensure data integrity.
* ⏰ **Scheduled Jobs**: Automated background tasks (e.g., cleanup, notifications).
* 🧪 **Unit & Integration Tests**: Covering CRUD operations and service layers to ensure reliability.
* 🕵️ **Interceptor Logic**: Custom interceptor for request/response monitoring, with a hidden easter egg message.

---

## 🚀 Getting Started

### 1️⃣ Clone the Repository

```bash
git clone https://github.com/yourusername/MyMovieApp.git
cd MyMovieApp
```

### 2️⃣ Configure `application.yaml`

Replace placeholders with your local credentials and API keys:

```yaml
spring:
  datasource:
    username: ${db_username}
    password: ${db_password}
    url: jdbc:mysql://localhost:3306/my_movie_app?allowPublicKeyRetrieval=true&useSSL=false&createDatabaseIfNotExist=true&serverTimezone=UTC
cloudinary:
  cloud-name: ${CLOUDINARY_NAME}
  api-key: ${CLOUDINARY_KEY}
  api-secret: ${CLOUDINARY_SECRET}
mail:
  host: localhost
  port: 1025
  username: ${MAIL_USER:username}
  password: ${MAIL_PASSWORD:password}
```

### 3️⃣ Start MailHog (Docker)

```bash
docker run -d -p 1025:1025 -p 8025:8025 mailhog/mailhog
```

👉 Access email inbox at: [http://localhost:8025](http://localhost:8025)

### 4️⃣ Run the Application

```bash
mvn spring-boot:run
```

### 5️⃣ Access Points

* App: [http://localhost:8080](http://localhost:8080)
* MailHog UI: [http://localhost:8025](http://localhost:8025)

---

## 🔒 User Roles

| Role      | Permissions                     |
| --------- | ------------------------------- |
| **USER**  | Access basic CRUD functionality |
| **ADMIN** | Manage content and users        |
| **ROOT**  | Full system control             |

