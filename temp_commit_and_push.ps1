# PowerShell script to commit and push changes
Set-Location "C:\Users\vlf86\Documents\GitHub\EduLearn-Automation-Framework"

Write-Host "Current directory: $(Get-Location)" -ForegroundColor Green

Write-Host "Adding all changes to Git..." -ForegroundColor Yellow
git add .

Write-Host "Committing changes..." -ForegroundColor Yellow
git commit -m "Fix CI/CD issues: Enable headless mode, improve browser config, and enhance GitHub Actions workflow

- Enable headless=true in config.properties for CI/CD environments  
- Add CI-specific Chrome options (--no-sandbox, --disable-dev-shm-usage, etc.)
- Improve GitHub Actions workflow with better error handling and directory creation
- Update pom.xml with system property support and memory optimization
- Enhance ConfigManager to prioritize system properties over config file
- Add config-ci.properties for dedicated CI configuration
- Update README with troubleshooting and CI/CD configuration guide
- Add parallel execution support and better test failure handling"

Write-Host "Pushing changes to GitHub..." -ForegroundColor Yellow
git push origin main

Write-Host "Done! Changes have been committed and pushed to GitHub." -ForegroundColor Green
Write-Host "Press any key to continue..."
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
