@echo off
REM Script to commit and push CI/CD fixes

echo Committing CI/CD fixes to repository...

REM Add all changed files
git add .github/workflows/main.yml
git add pom.xml  
git add src/test/resources/config-ci.properties

REM Commit with descriptive message
git commit -m "Fix CI/CD pipeline issues: Update GitHub Actions workflow with latest action versions (v4), fix Xvfb installation and display setup, remove deprecated JVM arguments, improve parallel test execution, update test URLs"

REM Push to main branch
git push origin main

echo Changes pushed successfully!
echo Check your GitHub Actions at: https://github.com/Unaivero/EduLearn-Automation-Framework/actions

pause