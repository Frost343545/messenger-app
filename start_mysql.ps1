Write-Host "🔧 Запуск MySQL службы..." -ForegroundColor Green
Write-Host ""

# Попытка найти и запустить MySQL службу
$mysqlServices = @("MySQL80", "MySQL90", "MySQL", "MySQL9.4")

foreach ($service in $mysqlServices) {
    try {
        $serviceObj = Get-Service -Name $service -ErrorAction SilentlyContinue
        if ($serviceObj) {
            Write-Host "✅ Найдена служба: $service" -ForegroundColor Green
            
            if ($serviceObj.Status -eq "Running") {
                Write-Host "✅ Служба $service уже запущена!" -ForegroundColor Green
                break
            }
            
            Write-Host "🔄 Запуск службы $service..." -ForegroundColor Yellow
            Start-Service -Name $service
            Write-Host "✅ Служба $service запущена успешно!" -ForegroundColor Green
            break
        }
    }
    catch {
        Write-Host "❌ Ошибка с службой $service : $($_.Exception.Message)" -ForegroundColor Red
    }
}

Write-Host ""
Write-Host "📋 Если служба не найдена, выполните вручную:" -ForegroundColor Cyan
Write-Host "1. Нажмите Win + R" -ForegroundColor White
Write-Host "2. Введите services.msc" -ForegroundColor White
Write-Host "3. Найдите MySQL службу" -ForegroundColor White
Write-Host "4. Правый клик → Запустить" -ForegroundColor White

Write-Host ""
Write-Host "🎯 После запуска службы попробуйте снова:" -ForegroundColor Cyan
Write-Host "Пуск → MySQL → MySQL Server 9.4 → MySQL Command Line Client" -ForegroundColor White

Write-Host ""
Read-Host "Нажмите Enter для выхода"
