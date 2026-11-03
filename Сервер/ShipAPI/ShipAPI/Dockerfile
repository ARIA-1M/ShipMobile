# Используем SDK для сборки
FROM mcr.microsoft.com/dotnet/sdk:8.0 AS build
WORKDIR /app

# Копируем csproj и восстанавливаем зависимости (для кэша)
COPY *.csproj ./
RUN dotnet restore

# Копируем всё остальное и публикуем
COPY . ./
RUN dotnet publish -c Release -o out

# Используем runtime для запуска (образ меньше)
FROM mcr.microsoft.com/dotnet/aspnet:8.0
WORKDIR /app
COPY --from=build /app/out .

# Render требует порт 8080 для бесплатного тарифа [citation:13]
ENV ASPNETCORE_URLS=http://+:8080
EXPOSE 8080

ENTRYPOINT ["dotnet", "ShipAPI.dll"]