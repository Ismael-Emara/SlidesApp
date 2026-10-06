# Slides App - Auto APK Build

This project includes a GitHub Actions workflow that automatically builds a debug APK on every push.

## How to get your APK (no Android Studio needed)

### 1. Push to GitHub
```bash
git init
git add .
git commit -m "Initial commit"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/YOUR_REPO.git
git push -u origin main
```

### 2. GitHub Actions builds automatically
- Go to your repo on GitHub
- Click **Actions** tab
- Wait for "Build Debug APK" workflow to complete (green checkmark)

### 3. Download APK
- Click the completed workflow run
- Scroll to **Artifacts** section
- Download `app-debug-apk.zip`
- Extract → `app-debug.apk` is your installable APK

## Manual trigger
Go to Actions → Build Debug APK → **Run workflow** → **Run workflow** (builds without pushing)

## Local build (if on x86_64 Linux/macOS/Windows)
```bash
./gradlew assembleDebug
# APK at: app/build/outputs/apk/debug/app-debug.apk
```

## Project structure
- 30 slides with swipe navigation
- Prev/Next buttons + jump-to-slide FAB
- Interactive action buttons on slides 5, 10, 15, 20, 25, 30
- Material 3 theming