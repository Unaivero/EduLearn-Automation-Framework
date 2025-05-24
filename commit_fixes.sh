#!/bin/bash

# Script to commit and push CI/CD fixes

echo "Committing CI/CD fixes to repository..."

# Add all changed files
git add .github/workflows/main.yml
git add pom.xml  
git add src/test/resources/config-ci.properties

# Commit with descriptive message
git commit -m "Fix CI/CD pipeline issues:

- Update GitHub Actions workflow with latest action versions (v4)
- Fix Xvfb installation and display setup
- Remove deprecated -XX:MaxPermSize JVM argument
- Improve parallel test execution configuration
- Update test URLs to use httpbin.org for testing
- Add proper environment variable handling"

# Push to main branch
git push origin main

echo "Changes pushed successfully!"
echo "Check your GitHub Actions at: https://github.com/Unaivero/EduLearn-Automation-Framework/actions"