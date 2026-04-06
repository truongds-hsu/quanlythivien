## 🚀 **PHẦN: ĐƯA PROJECT LÊN GITHUB**

### **Bước 1: Tạo Repository GitHub**

1. Vào https://github.com/new
2. Nhập tên: `LibraryManagement`
3. Nhập mô tả: `Library Management System with Java 25 & Spring Boot 3.5.0`
4. Chọn **Public** hoặc **Private**
5. Click "Create repository"

**Sẽ nhận được URL**: `https://github.com/your-username/LibraryManagement.git`

---

### **Bước 2: Khởi Tạo Git Trong Project (Nếu Chưa Có)**

```bash
# Vào thư mục project
cd /Users/alanhjhj/Downloads/LibraryManagement

# Khởi tạo git
git init

# Thêm remote (thay your-username bằng username GitHub của anh)
git remote add origin https://github.com/your-username/LibraryManagement.git

# Verify
git remote -v
```

---

### **Bước 3: Add Tất Cả File Vào Git**

```bash
# Add tất cả file
git add .

# Kiểm tra file được add chưa
git status
```

---

### **Bước 4: Commit Lần Đầu**

```bash
git commit -m "Initial commit: LibraryManagement with Java 25, Spring Boot 3.5.0

- Models: Book, Member, BorrowRecord, Fine, Reservation
- Services: BookService, MemberService, BorrowService, FineService, ReservationService
- Repositories: All setup with custom queries
- Test data: 10 books, 5 members, transaction records
- Configuration: H2 database, data.sql auto-load
- Team structure: 5 persons with assigned features"
```

---

### **Bước 5: Push Lên GitHub**

```bash
# Push branch main
git branch -M main
git push -u origin main
```

**Output khi thành công:**
```
Enumerating objects: 50, done.
Counting objects: 100% (50/50), done.
Writing objects: 100% (50/50), 2.15 MiB | 1.5 MiB/s
...
remote: Create a pull request for 'main' on GitHub by visiting:
remote:      https://github.com/your-username/LibraryManagement/pull/new/main
```

---

### **Bước 6: Kiểm Tra Trên GitHub**

Vào: `https://github.com/your-username/LibraryManagement`

Sẽ thấy tất cả file của project!

---

## 👥 **PHẦN: SHARE VỚI TEAM MEMBERS**

### **Cách 1: Cho Phép Access (GitHub Settings)**

1. Vào repo → Settings
2. Collaborators → Add people
3. Thêm GitHub username của team members
4. Choose role: **Collaborator** hoặc **Reviewer**

---

### **Cách 2: Team Members Clone Project**

```bash
# Clone repository
git clone https://github.com/your-username/LibraryManagement.git

# Vào thư mục
cd LibraryManagement

# Tạo branch riêng cho team member (VD: Person A)
git checkout -b feature/person-a-book-management

# Hoặc update branch chính
git pull origin main
```

---

### **Cách 3: Team Members Submit Code (Pull Request)**

**Person A làm xong BookController:**

```bash
# Commit code
git add .
git commit -m "Feature: Implement BookController with 5 endpoints

- GET /api/books: List all books
- POST /api/books: Add new book
- GET /api/books/{id}: Get book detail
- PUT /api/books/{id}: Update book
- DELETE /api/books/{id}: Delete book

Tests: 3 unit tests + E2E tests"

# Push lên GitHub
git push origin feature/person-a-book-management
```

**Rồi tạo Pull Request (PR) trên GitHub:**

1. Vào repo GitHub
2. Click "Compare & pull request"
3. Từ branch `feature/person-a-book-management` vào `main`
4. Nhập description + click "Create pull request"
5. Anh review code → Approve → Merge

---

### **Cách 4: Update Code Mới Nhất**

```bash
# Trước khi code, cập nhật code từ GitHub
git pull origin main

# Hoặc nếu đang ở branch khác
git checkout main
git pull origin main
```

---

## 📋 **Quy Trình Git Cho Team**

### **Workflow Hàng Ngày:**

```bash
# Sáng (Update code mới)
git checkout main
git pull origin main

# Code feature của mình
git checkout -b feature/person-a-[feature-name]
# Và code...

# Trưa (Commit)
git add .
git commit -m "Feature: [description]"
git push origin feature/person-a-[feature-name]

# Chiều (Tạo PR + Review)
# Trên GitHub: Click "Compare & pull request"
# Anh review → Approve → Merge

# Cuối ngày (Update main)
git checkout main
git pull origin main
```

---

## 🔧 **Các Lệnh Git Thường Dùng**

| Lệnh | Tác Dụng |
|------|---------|
| `git status` | Xem status của file |
| `git add .` | Add tất cả file |
| `git commit -m "message"` | Commit với message |
| `git push origin [branch]` | Push lên GitHub |
| `git pull origin main` | Update code từ GitHub |
| `git checkout -b [branch]` | Tạo branch mới |
| `git checkout [branch]` | Switch branch |
| `git log` | Xem history commits |
| `git branch -a` | Xem tất cả branches |

---

## ⚠️ **Lỗi Git Thường Gặp**

### **Lỗi 1: "Permission denied (publickey)" - Git Đòi Deploy Key Khi Push**

**Tình Huống:**
```bash
$ git push -u origin main

Permission denied (publickey).
fatal: Could not read from remote repository.

Please make sure you have the correct access rights
and the repository exists.
```

**Nguyên Nhân:** GitHub chưa biết đó là anh, cần xác thực.

---

### **✅ Giải Pháp - Dùng Personal Access Token (Dễ Nhất)**

#### **Bước 1: Tạo Personal Access Token Trên GitHub**

1. Vào: https://github.com/settings/tokens
2. Click "Generate new token (classic)"
3. Nhập tên: `LibraryManagement-Token`
4. Check box: **repo** (Full control of private repositories)
5. Click "Generate token"
6. **COPY TOKEN NÀY** (chỉ hiển thị 1 lần)

**Sẽ trông như:** `ghp_1a2b3c4d5e6f7g8h9i0j1k2l3m4n5o6p7qr`

---

#### **Bước 2: Thay Đổi Git Remote URL Để Dùng Token**

```bash
# Bước 1: Xem current remote
git remote -v

# Output:
# origin  https://github.com/your-username/LibraryManagement.git (fetch)
# origin  https://github.com/your-username/LibraryManagement.git (push)

# Bước 2: Update remote với token
# Format: https://[token]@github.com/[username]/[repo].git
git remote set-url origin https://ghp_1a2b3c4d5e6f7g8h9i0j1k2l3m4n5o6p7qr@github.com/your-username/LibraryManagement.git

# Bước 3: Verify
git remote -v
```

**Output khi thành công:**
```
origin  https://ghp_1a2b3c4d5e6f7g8h9i0j1k2l3m4n5o6p7qr@github.com/your-username/LibraryManagement.git (fetch)
origin  https://ghp_1a2b3c4d5e6f7g8h9i0j1k2l3m4n5o6p7qr@github.com/your-username/LibraryManagement.git (push)
```

---

#### **Bước 3: Thử Push Lại**

```bash
git push -u origin main
```

**Kết quả:**
```
Enumerating objects: 50, done.
Counting objects: 100% (50/50), done.
Writing objects: 100% (50/50), 2.15 MiB | 1.5 MiB/s
...
 * [new branch]      main -> main
Branch 'main' set up to track remote branch 'main' from 'origin'.
```

✅ **Thành công!**

---

### **⚙️ Giải Pháp 2 - Setup SSH Key (Nâng Cao, Bảo Mật Hơn)**

#### **Bước 1: Tạo SSH Key Trên macOS**

```bash
# Tạo key với email GitHub của anh
ssh-keygen -t ed25519 -C "your-email@example.com"

# Press Enter 3 lần (không cần nhập passphrase)
```

**Output:**
```
Generating public/private ed25519 key pair.
Enter file in which to save the key (/Users/alanhjhj/.ssh/id_ed25519):
Enter passphrase (empty for no passphrase):
Enter same passphrase again:
Your identification has been saved in /Users/alanhjhj/.ssh/id_ed25519
Your public key has been saved in /Users/alanhjhj/.ssh/id_ed25519.pub
```

---

#### **Bước 2: Add SSH Key Vào SSH Agent**

```bash
# Start SSH agent
eval "$(ssh-agent -s)"

# Add key
ssh-add ~/.ssh/id_ed25519
```

---

#### **Bước 3: Add Public Key Vào GitHub**

```bash
# Copy public key
cat ~/.ssh/id_ed25519.pub

# Output sẽ là chuỗi dài như:
# ssh-ed25519 AAAAC3NzaC1lZDI1NTE5AAAAI... your-email@example.com
```

**Trên GitHub:**
1. Vào: https://github.com/settings/keys
2. Click "New SSH key"
3. Title: `MacBook-LibraryManagement`
4. Key type: Authentication Key
5. Paste public key (từ `cat` ở trên)
6. Click "Add SSH key"

---

#### **Bước 4: Thay Git Remote Sang SSH**

```bash
# Thay từ HTTPS sang SSH
git remote set-url origin git@github.com:your-username/LibraryManagement.git

# Verify
git remote -v
```

**Output:**
```
origin  git@github.com:your-username/LibraryManagement.git (fetch)
origin  git@github.com:your-username/LibraryManagement.git (push)
```

---

#### **Bước 5: Test SSH Connection**

```bash
ssh -T git@github.com
```

**Output khi thành công:**
```
Hi your-username! You've successfully authenticated, but GitHub does not provide shell access.
```

✅ **SSH setup xong!**

---

#### **Bước 6: Push Lên GitHub**

```bash
git push -u origin main
```

✅ **Thành công!**

---

### **🎯 So Sánh 2 Phương Pháp**

| Phương Pháp | Ưu Điểm | Nhược Điểm |
|-------------|--------|-----------|
| **Personal Access Token (HTTPS)** | Dễ setup (5 phút), không cần advanced config | Token lộ trong URL, cần giữ kín |
| **SSH Key** | Bảo mật hơn, không lộ credential | Setup phức tạp hơn, mất 15 phút |

**Khuyến cáo:** 
- ✅ **Anh muốn nhanh?** Dùng Personal Access Token (HTTPS)
- ✅ **Anh muốn bảo mật?** Dùng SSH Key

---

### **💡 Nếu Anh Quên Token Hoặc Muốn Regenerate**

```bash
# Xem current remote (có token trong URL không?)
git remote -v

# Nếu muốn xóa token khỏi URL
git remote set-url origin https://github.com/your-username/LibraryManagement.git

# Rồi dùng HTTPS + credential helper (Git sẽ nhớ password)
# macOS:
git config --global credential.helper osxkeychain

# Lần sau push, Git sẽ hỏi username + password (hoặc personal access token)
```

---

### **Lỗi 1b: "[rejected] main -> main (fetch first)" - GitHub Có File Ngoài Local**

**Tình Huống:**
```bash
$ git push -u origin main

To https://github.com/whois-mrWater/LibraryManagement.git
 ! [rejected]        main -> main (fetch first)
error: failed to push some refs to 'https://github.com/whois-mrWater/LibraryManagement.git'
hint: Updates were rejected because the remote contains work that you do not
hint: have locally. This is usually caused by another repository pushing to
hint: the same ref.
```

**Nguyên Nhân:**
- GitHub repo có file ngoài local (VD: README.md được tạo tự động khi tạo repo)
- Git đòi anh pull trước khi push để sync

---

### **✅ Giải Pháp - Pull Trước, Push Sau**

#### **Bước 1: Pull Code Từ GitHub Về Local**

```bash
# Pull remote changes
git pull origin main
```

**Output:**
```
remote: Enumerating objects: 3, done.
remote: Counting objects: 100% (3/3), done.
remote: Compressing objects: 100% (2/2), done.
remote: Unpacking objects: 100% (3/3), done.
From https://github.com/whois-mrWater/LibraryManagement
 * branch            main       -> FETCH_HEAD
 * [new branch]      main       -> origin/main
Already up to date.
```

---

#### **Bước 2: Check Status (Có Xung Đột Không?)**

```bash
git status
```

**Nếu output hiện "both added" → Có xung đột**

**Xung đột sẽ như:**
```
both added:      README.md
both modified:   pom.xml
```

---

#### **Bước 3A: Nếu Không Có Xung Đột - Cứ Push**

```bash
git push -u origin main
```

✅ **Thành công!**

---

#### **Bước 3B: Nếu Có Xung Đột - Resolve Xung Đột**

**VD: Có xung đột ở README.md**

```bash
# 1. Mở file README.md trong editor
cat README.md

# Sẽ thấy:
# <<<<<<< HEAD
# Content anh push
# =======
# Content từ GitHub
# >>>>>>> origin/main
```

**Sửa file:**
- Xóa `<<<<<<< HEAD`, `=======`, `>>>>>>> origin/main`
- Giữ content nào thích hợp

**Sau khi sửa:**
```bash
# Add file đã fix xung đột
git add .

# Commit
git commit -m "Resolve conflicts: Merge README.md from remote"

# Push lên
git push -u origin main
```

✅ **Thành công!**

---

### **Lỗi 1c: "Need to specify how to reconcile divergent branches" - Git Vò Đầu**

**Tình Huống:**
```bash
$ git pull origin main

hint: You have divergent branches and need to specify how to reconcile them.
hint: You can do so by running one of the following commands:
hint:
hint:   git config pull.rebase false  # merge
hint:   git config pull.rebase true   # rebase
hint:   git config pull.ff only       # fast-forward only
hint:
fatal: Need to specify how to reconcile divergent branches.
```

**Nguyên Nhân:**
- Local branch và remote branch có history khác nhau
- Git không biết merge hay rebase
- (Thường xảy ra khi tạo repo GitHub có README.md, local cũng có commit riêng)

**Status lúc này:**
```bash
$ git status

On branch main
Changes not staged for commit:
  (use "git add <file>..." to update working directory)
        modified:   GUIDE_GIT.md
```

---

### **✅ Giải Pháp - Merge (Đơn Giản Nhất)**

#### **Bước 1: Commit Local Changes Trước**

```bash
# Git cần anh commit local changes trước khi merge
git add .
git commit -m "Update GUIDE_GIT.md with error handling"
```

**Output:**
```
[main abc1234] Update GUIDE_GIT.md with error handling
 1 file changed, 50 insertions(+), 5 deletions(-)
```

---

#### **Bước 2: Pull Với --no-rebase Option (Merge)**

```bash
# Merge remote changes vào local
git pull --no-rebase origin main
```

**Output:**
```
From https://github.com/whois-mrWater/LibraryManagement
 * branch            main       -> FETCH_HEAD
Merge made by the 'ort' strategy.
 README.md | 5 ++
 1 file changed, 5 insertions(+)
```

✅ **Merge thành công!**

---

#### **Bước 3: Push Lên GitHub**

```bash
git push -u origin main
```

✅ **Done!**

---

### **💡 Giải Thích - Merge vs Rebase**

**Merge (--no-rebase):** ✅ KHUYÊN DÙNG
- Tạo 1 commit mới gộp 2 branches
- An toàn, rõ ràng, dễ hiểu
- History sạch hơn

**Rebase (--rebase):**
- Viết lại history
- Dùng khi muốn linear history
- Phức tạp, dễ gây xung đột

**VD Merge:**
```
local:  A --- B --- C (anh's commit)
                 \
                  D (merge commit)
                 /
remote: A --- B --- E (GitHub's commit)

= Kết quả: A --- B --- C --- D --- E
           (gộp cả 2 branches lại)
```

---

### **🎯 Quick Reference - Divergent Branches**

```bash
# Khi gặp lỗi "divergent branches":

# Bước 1: Commit local changes
git add .
git commit -m "Your message"

# Bước 2: Merge (pull --no-rebase)
git pull --no-rebase origin main

# Bước 3: Push
git push -u origin main
```

---

### **⚠️ Các Tình Huống Khác**

#### **Nếu Anh Muốn Dùng Rebase (Advanced)**

```bash
# Rewrite history (phức tạp hơn)
git pull --rebase origin main

# Nếu có conflict:
# 1. Sửa file conflict
# 2. git add .
# 3. git rebase --continue
# 4. git push
```

**Khuyên:** Beginner nên dùng **merge (--no-rebase)**, dễ hiểu hơn.

---

#### **Nếu Muốn Set Default (Cho Tương Lai)**

```bash
# Set merge as default for this repo
git config pull.rebase false

# Hoặc global (tất cả repos)
git config --global pull.rebase false
```

**Sau đó, `git pull` sẽ tự dùng merge.**

---

### **Lỗi 2: "Conflicts" (Xung Đột Code)**

```bash
# Khi merge xung đột
# Sửa conflict trong code editor
# Rồi:
git add .
git commit -m "Resolve conflicts"
git push origin [branch]
```

---

### **Lỗi 3: "File Too Large"**

```bash
# Xóa file lớn khỏi Git
git rm --cached [file]
git commit -m "Remove large file"
git push
```

---

## 📚 **GitHub Best Practices**

✅ **LÀM:**
- Tạo branch riêng cho mỗi feature
- Commit messages rõ ràng, chi tiết
- Pull request trước khi merge vào main
- Review code của team members

❌ **KHÔNG LÀM:**
- Push trực tiếp vào main
- Commit với message "fix", "update", v.v.
- Quên pull code mới trước khi code
- Commit code k compile được

---