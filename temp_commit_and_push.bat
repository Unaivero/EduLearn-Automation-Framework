@echo off
cd /d "C:\Users\vlf86\Documents\GitHub\EduLearn-Automation-Framework"

echo Adding all changes to Git...
git add .

echo Committing changes...
git commit -m "Fix CI/CD issues: Enable headless mode, improve browser config, and enhance GitHub Actions workflow

- Enable headless=true in config.properties for CI/CD environments
- Add CI-specific Chrome options (--no-sandbox, --disable-dev-shm-usage, etc.)
- Improve GitHub Actions workflow with better error handling and directory creation
- Update pom.xml with system property support and memory optimization
- Enhance ConfigManager to prioritize system properties over config file
- Add config-ci.properties for dedicated CI configuration
- Update README with troubleshooting and CI/CD configuration guide
- Add parallel execution support and better test failure handling"

echo Pushing changes to GitHub...
git push origin main

echo Done! Changes have been committed and pushed to GitHub.
pause
